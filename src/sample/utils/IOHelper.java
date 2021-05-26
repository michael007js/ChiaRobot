package sample.utils;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * IO操作的工具类
 *
 * @author yjd
 */
@SuppressWarnings("ALL")
public class IOHelper {

    private IOHelper() {
    }

    /**
     * 根据指定的文件名从classpath下把该文件加载成InputStream实例
     *
     * @param path
     * @return
     */
    public static InputStream getInputStreamFromClassPath(String path) {
        return Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(path);
    }

    /**
     * 创建目录
     *
     * @param dirFile
     * @return
     * @throws RuntimeException
     */
    public static boolean createDir(File dirFile) throws RuntimeException {
        boolean flag = false;
        if (dirFile.exists()) {
            flag = true;
        } else {
            flag = dirFile.mkdirs();
        }
        return flag;
    }

    /**
     * 删除目录
     *
     * @param dirFile
     * @return
     * @throws RuntimeException
     */
    public static boolean deleteDir(File dirFile) throws RuntimeException {
        boolean flag = false;
        if (!dirFile.exists()) {
            flag = true;
        } else {
            if (deleteSubFiles(dirFile)) {
                flag = dirFile.delete();
            }
        }
        return flag;
    }

    /**
     * 清空指定目录下的所有文件
     *
     * @param dir
     */
    public static boolean deleteSubFiles(File dir) {
        boolean flag = false;
        if (dir.exists() && dir.isDirectory()) {
            File[] subs = dir.listFiles();
            int succ = 0;
            int fail = 0;
            int length = subs == null ? 0 : subs.length;
            File sub = null;
            try {
                for (int i = 0; i < length; i++) {
                    sub = subs[i];
                    if (sub.delete()) {
                        succ++;
                    } else {
                        fail++;
                    }
                }
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 获取指定文件的扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExtend(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    /**
     * 使用当前日期时间来产生"yyyyMMddHHmmssSSS"的字符串，主要用来做文件名
     *
     * @return
     */
    public static String generateFileNameByDateTime() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }

    /**
     * 把源文件的内容复制到目标文件
     *
     * @param src  源文件
     * @param dest 目标文件
     */
    public static void copy(File src, File dest) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        byte[] b = new byte[8192];
        try {
            bis = new BufferedInputStream(new FileInputStream(src));
            bos = new BufferedOutputStream(new FileOutputStream(dest));

            for (int count = -1; (count = bis.read(b)) != -1; ) {
                bos.write(b, 0, count);
            }
            bos.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            close(bis);
            close(bos);
        }
    }

    /**
     * 把源图片srcImg缩小至指定的宽度和高度并存放至dest目标文件
     *
     * @param srcImg
     * @param dest
     * @param outWidth
     * @param outHeight
     */
    public static void resizeToFile(BufferedImage srcImg, File dest,
                                    int outWidth, int outHeight) throws RuntimeException {
        BufferedImage pbFinalOut = null;
        if (srcImg.getWidth() == outWidth && srcImg.getHeight() == outHeight) {
            pbFinalOut = srcImg;
        } else {
            pbFinalOut = IOHelper.scaleImage(srcImg, outWidth, outHeight);
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dest);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(pbFinalOut);
        } catch (ImageFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(out);
        }
    }

    /**
     * 把源图像缩小成指定宽高
     *
     * @param srcImg    源图片
     * @param outWidth  宽
     * @param outHeight 高
     * @return 压缩后的图像对象
     */
    public static BufferedImage scaleImage(BufferedImage srcImg, int outWidth,
                                           int outHeight) {
        int width = srcImg.getWidth();
        int height = srcImg.getHeight();
        int[] rgbArray = srcImg.getRGB(0, 0, width, height, null, 0, width);
        BufferedImage pbFinalOut = new BufferedImage(outWidth, outHeight,
                BufferedImage.TYPE_INT_RGB);
        double hScale = ((double) width) / ((double) outWidth);// 宽缩小的倍数
        double vScale = ((double) height) / ((double) outHeight);// 高缩小的倍数

        int winX0, winY0, winX1, winY1;
        int valueRGB = 0;
        long R, G, B;
        int x, y, i, j;
        int n;
        for (y = 0; y < outHeight; y++) {
            winY0 = (int) (y * vScale + 0.5);// 得到原图高的Y坐标
            if (winY0 < 0) {
                winY0 = 0;
            }
            winY1 = (int) (winY0 + vScale + 0.5);
            if (winY1 > height) {
                winY1 = height;
            }
            for (x = 0; x < outWidth; x++) {
                winX0 = (int) (x * hScale + 0.5);
                if (winX0 < 0) {
                    winX0 = 0;
                }
                winX1 = (int) (winX0 + hScale + 0.5);
                if (winX1 > width) {
                    winX1 = width;
                }
                R = 0;
                G = 0;
                B = 0;
                for (i = winX0; i < winX1; i++) {
                    for (j = winY0; j < winY1; j++) {
                        valueRGB = rgbArray[width * j + i];
                        R += getRedValue(valueRGB);
                        G += getGreenValue(valueRGB);
                        B += getBlueValue(valueRGB);
                    }
                }
                n = (winX1 - winX0) * (winY1 - winY0);
                R = (int) (((double) R) / n + 0.5);
                G = (int) (((double) G) / n + 0.5);
                B = (int) (((double) B) / n + 0.5);
                valueRGB = comRGB(clip((int) R), clip((int) G), clip((int) B));
                pbFinalOut.setRGB(x, y, valueRGB);
            }
        }
        return pbFinalOut;
    }

    private static int clip(int x) {
        if (x < 0)
            return 0;
        if (x > 255)
            return 255;
        return x;
    }

    private static int getRedValue(int rgbValue) {
        int temp = rgbValue & 0x00ff0000;
        return temp >> 16;
    }

    private static int getGreenValue(int rgbValue) {
        int temp = rgbValue & 0x0000ff00;
        return temp >> 8;
    }

    private static int getBlueValue(int rgbValue) {
        return rgbValue & 0x000000ff;
    }

    private static int comRGB(int redValue, int greenValue, int blueValue) {
        return (redValue << 16) + (greenValue << 8) + blueValue;
    }


    public static boolean writeString(String filepath, boolean append, String content) {
        try {
            checkPath(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filepath)), "utf-8"));
            out.write(content + "\r\n");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(out);
        }

    }


    /**
     * 同时关闭输入流和输出流，并把可能抛出的异常转换成RuntimeException
     *
     * @param is
     * @param os
     */
    public static void close(InputStream is, OutputStream os) {
        close(is);
        close(os);
    }

    /**
     * 关闭输入流的工具方法，并把可能抛出的异常转换成RuntimeException
     *
     * @param is
     */
    public static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流的工具方法，并把可能抛出的异常转换成RuntimeException
     *
     * @param os
     */
    public static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(BufferedWriter br) {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean checkPath(String filepath) throws IOException {
        File file = new File(filepath);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        return file.createNewFile();
    }
}
