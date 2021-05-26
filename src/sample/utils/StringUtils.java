package sample.utils;

import java.text.NumberFormat;
import java.util.List;

/**
 *  字符串相关工具类
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        if (s == null){
            return true;
        }else {
            if (s.length() == 0){
                return true;
            }else {
                if ("".equals(s.toString().trim())||"null".equals(s)||"NULL".equals(s)){
                    return true;
                }
            }
        }
        return false;
    }

    public static String dislodgeEmptyToEmpty(String s) {
        if(isEmpty(s)){
            return "";
        }
        return s;
    }

    public static String dislodgeEmptyToZero(String s) {
        if(isEmpty(s)){
            return "0";
        }
        return s;
    }

    /**
     * 将double转为数值，并最多保留num位小数。例如当num为2时，1.268为1.27，1.2仍为1.2；1仍为1，而非1.00;100.00则返回100。
     *
     * @param d 数值
     * @param num 小数位数
     * @return 数值
     */
    public static String double2String(double d, int num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(num);//保留两位小数
        nf.setGroupingUsed(false);//去掉数值中的千位分隔符

        String temp = nf.format(d);
        if (temp.contains(".")) {
            String s1 = temp.split("\\.")[0];
            String s2 = temp.split("\\.")[1];
            for (int i = s2.length(); i > 0; --i) {
                if (!s2.substring(i - 1, i).equals("0")) {
                    return s1 + "." + s2.substring(0, i);
                }
            }
            return s1;
        }
        return temp;
    }

    /**
     * 分割字符串返回集合
     * @param str  被分割的字符串
     * @param split  分割标识
     * @return
     */
    public static List<String> splitString(List<String> list,String str, String split){
        String[] strings = str.split(split);
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        return list;
    }

    /**
     * 判断是否属于 中文 英文 数字
     * @param string
     * @return
     */
    public static boolean matchesChineseEnglishNumerals(String string){
        return string.matches("^[\u4e00-\u9fa5_a-zA-Z0-9]*");
    }

    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}