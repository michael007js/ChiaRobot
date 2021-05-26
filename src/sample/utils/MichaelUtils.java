package sample.utils;


import com.sun.management.OperatingSystemMXBean;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Region;
import javafx.stage.Screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MichaelUtils {


    /**
     * 重启自身
     */
    public static void restartApp() {
        if (PathUtil.projectPath.endsWith(".jar")) {
            if (!StringUtils.isEmpty(MichaelUtils.launchEXE(PathUtil.projectPath))) {
                System.exit(1);
            } else {
                AlertUtils.showWarn("警告：", "重启失败" + PathUtil.projectPath);
            }
        } else {
            AlertUtils.showWarn("警告：", "重启失败,调试版本不支持！\nI will be back!");
        }
    }


    /**
     * 调用windows本地目录程序
     */
    public static String launchEXE(String exePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("cmd /c " + exePath);
            InputStream fis = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line = null;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return stringBuilder.toString();
        }
        return stringBuilder.toString();
    }


    public static String runByCMD(String command) {
        Runtime runtime = Runtime.getRuntime();
        StringBuffer b = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(runtime.exec(command).getInputStream()));
            //StringBuffer b = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                b.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return b.toString();
        }
        return b.toString();
    }

    /**
     * 调用windows本地目录程序
     */
    public static boolean launchEXEByRundll32(String exePath) {
        Runtime runtime = Runtime.getRuntime();
        final String cmd = "rundll32 url.dll FileProtocolHandler file://" + exePath;
        try {
            runtime.exec(cmd);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * RxJava包装
     */
    public static <T> void goObserver(Observable<T> observable, DisposableObserver<T> disposableObserver) {
        goObserver(observable, Schedulers.io(), disposableObserver);
    }

    public static <T> void goObserver(Observable<T> observable, Scheduler scheduler, DisposableObserver<T> disposableObserver) {
        observable.subscribeOn(scheduler)
                .subscribe(disposableObserver);
    }

    /**
     * 获取控件
     *
     * @param application 应用基类
     * @param layoutName  布局名称  main.fxml
     * @param widgetName  控件id #edit_database_address
     * @return 控件对象, 需强转
     */
    private static Object createView(Application application, String layoutName, String widgetName) throws IOException {
        Parent parent = FXMLLoader.load(application.getClass().getResource("main.fxml"));
        return parent.lookup("#edit_database_address");
    }


    /**
     * 取两个文本之间的文本值
     *
     * @param text  源文本 比如：欲取全文本为 12345
     * @param left  文本前面
     * @param right 后面文本
     * @return 返回 String
     */
    public static String getSubString(String text, String left, String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }


    /**
     * 获取栈轨迹
     */
    public static String getStackTrace() {
        StringBuffer err = new StringBuffer();
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            err.append("\tat ");
            err.append(stack[i].toString());
            err.append("\n");
        }
        return err.toString();
    }

    /**
     * 获取错误轨迹
     */
    public static String getStackTrace(Throwable throwable) {
        StringBuffer err = new StringBuffer();
        err.append(throwable.getLocalizedMessage());
        StackTraceElement[] stack = throwable.getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            err.append("\tat ");
            err.append(stack[i].toString());
            err.append("\n");
        }
        return err.toString();
    }

    public static void autoSize(Region region, Screen screen) {
        if (true) {
            //待完善
            return;
        }
        double percent = screen.getDpi() / 100;
        Bounds boundsInParent = region.getBoundsInParent();
        Bounds layoutBounds = region.getLayoutBounds();
        Bounds size = region.getBoundsInLocal();
        LogUtils.e(screen.getDpi()/*141*/, screen.getDpi() / 100);
        region.setLayoutX(region.getLayoutX() * percent);
        region.setLayoutY(region.getLayoutY() * percent);
        region.setScaleX(percent);
        region.setScaleY(percent);

    }

    /**
     * 把文本设置到剪贴板（复制）
     */
    public static boolean setClipboardString(String text) {
        // 获取系统剪贴板
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(text);
        return clipboard.setContent(clipboardContent);

    }


    /**
     * 将Unicode转为UTF-8中文
     *
     * @param str 入参字符串
     * @return
     */
    public static String decodeUnicode(String str) {
        if (str == null) {
            return "";
        }
        Charset set = Charset.forName("UTF-16");
        Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
        if (p == null) {
            return "";
        }
        Matcher m = p.matcher(str);
        int start = 0;
        int start2 = 0;
        StringBuffer sb = new StringBuffer();
        while (m.find(start)) {
            start2 = m.start();
            if (start2 > start) {
                String seg = str.substring(start, start2);
                sb.append(seg);
            }
            String code = m.group(1);
            int i = Integer.valueOf(code, 16);
            byte[] bb = new byte[4];
            bb[0] = (byte) ((i >> 8) & 0xFF);
            bb[1] = (byte) (i & 0xFF);
            ByteBuffer b = ByteBuffer.wrap(bb);
            sb.append(String.valueOf(set.decode(b)).trim());
            start = m.end();
        }
        start2 = str.length();
        if (start2 > start) {
            String seg = str.substring(start, start2);
            sb.append(seg);
        }
        return sb.toString();
    }

    /**
     * 计算机可用内存
     *
     * @param total 是否为总内存
     * @return
     */
    public static long getMemory(boolean total) {
        OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return total ? osmb.getTotalPhysicalMemorySize() : osmb.getFreePhysicalMemorySize();

    }

    static DecimalFormat format = new DecimalFormat("###.00");

    /**
     * 格式化内存
     *
     * @param size 内存
     * @return
     */
    public static String formatMemory(long size) {
        StringBuffer bytes = new StringBuffer();

        if (size >= 1024L * 1024L * 1024L * 1024L) {
            double i = (size / (1024.0 * 1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("TB");
        } else if (size >= 1024L * 1024L * 1024L) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024L * 1024L) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024L) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024L) {
            bytes.append("0B");
        }
        return bytes.toString();
    }
}
