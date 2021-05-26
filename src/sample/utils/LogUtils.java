package sample.utils;



import sample.constant.AppConstant;

public class LogUtils {
    static String packageClassName;//类(完整包名不带后缀)
    static String classNameJava;//类名（java文件名）
    static String methodName;//方法名
    static int lineNumber;//行数

    private static void getMethodNames(StackTraceElement[] sElements) {
        classNameJava = sElements[1].getFileName();
        packageClassName = sElements[1].getClassName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(Object... object) {
        if (object == null && !AppConstant.IS_DEBUG) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        getMethodNames(new Throwable().getStackTrace());
        stringBuilder.append(methodName);
        stringBuilder.append("(").append(classNameJava).append(":").append(lineNumber).append(")");
        stringBuilder.append(" : ");
        for (int i = 0; i < object.length; i++) {
            if (object[i] != null) {
                stringBuilder.append(object[i].toString() + (i == object.length - 1 ? "" : ","));
            }
        }

        System.out.println(TimeUtils.getNowString("yyyy-MM-dd HH:mm:ss:SSS") + "日志" + packageClassName + stringBuilder.toString());
    }

}
