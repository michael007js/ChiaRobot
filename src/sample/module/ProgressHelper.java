package sample.module;

public class ProgressHelper {

    /**
     * 进入转换器
     */
    public static int getProgress(int lineCount) {

        return (int) (lineCount * 1.0f / 2646 * 100);
    }
}
