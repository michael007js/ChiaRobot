package sample.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2016/1/7.
 */
public class sysUtil {
    public static String getSign() {
        Random random = new Random();
        String str = getChars(Signtimes());//获取时间编码
        LogUtils.e(str);
        int sign = random.nextInt(999999999) + 1;//获取随机数
        str += "G" + getChars(sign + "");//获取随机数编码 在随机数编码前加G分隔符
        LogUtils.e(str);
        int ran = random.nextInt(6) + 2;//获取一个差值随机数
        String newString = "";
        for (int i = 0; i <= ran; i++) {
            int ranc = random.nextInt(str.length() - 3) + 2;
            newString = str.substring(0, ranc) + "C" + str.substring(ranc, str.length());//随机分割拼接打乱符
            str = newString;
        }
        return newString;
    }

    public static String getApiSign() {
        Random random = new Random();
        String str = getApiChars(ApiSigntimes());//获取时间编码
        int sign = random.nextInt(999999999) + 1;//获取随机数
        str += "h" + getChars(sign + "");//获取随机数编码 在随机数编码前加G分隔符
        int ran = random.nextInt(6) + 2;//获取一个差值随机数
        String newString = "";
        for (int i = 0; i <= ran; i++) {
            int ranc = random.nextInt(str.length() - 3) + 2;
            newString = str.substring(0, ranc) + "u" + str.substring(ranc, str.length());//随机分割拼接打乱符
            str = newString;
        }
        return newString;
    }

    public static String getDATASign() {
        Random random = new Random();
        String str = getDataChars(DataSigntimes());//获取时间编码
        int sign = random.nextInt(999999999) + 1;//获取随机数
        str += "t" + getChars(sign + "");//获取随机数编码 在随机数编码前加G分隔符
        int ran = random.nextInt(6) + 2;//获取一个差值随机数
        String newString = "";
        for (int i = 0; i <= ran; i++) {
            int ranc = random.nextInt(str.length() - 3) + 2;
            newString = str.substring(0, ranc) + "T" + str.substring(ranc, str.length());//随机分割拼接打乱符
            str = newString;
        }
        return newString;
    }

    public static String getSuperSign() {
        Random random = new Random();
        String str = getSuperChars(DataSupertimes());//获取时间编码
        int sign = random.nextInt(9999) + 1;//获取随机数
        str += "Q" + getChars(sign + "");//获取随机数编码 在随机数编码前加G分隔符
        int ran = random.nextInt(6) + 2;//获取一个差值随机数
        String newString = "";
        for (int i = 0; i <= ran; i++) {
            int ranc = random.nextInt(str.length() - 3) + 2;
            newString = str.substring(0, ranc) + "q" + str.substring(ranc, str.length());//随机分割拼接打乱符
            str = newString;
        }
        return newString;
    }

    public static String getSignMedia() {
        Random random = new Random();
        String str = getChars(times());//获取时间编码
        int ran = random.nextInt(6) + 2;//获取一个差值随机数
        String newString = str.substring(0, ran) + "C" + str.substring(ran, str.length());
        return newString;
    }

    public static String sign() {
        String str = getChars(times());
        return str;
    }

    private static String getChars(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case '0':
                    chars[i] = 'Z';
                    break;
                case '1':
                    chars[i] = 'O';
                    break;
                case '2':
                    chars[i] = 'T';
                    break;
                case '3':
                    chars[i] = 't';
                    break;
                case '4':
                    chars[i] = 'F';
                    break;
                case '5':
                    chars[i] = 'f';
                    break;
                case '6':
                    chars[i] = 'S';
                    break;
                case '7':
                    chars[i] = 's';
                    break;
                case '8':
                    chars[i] = 'E';
                    break;
                case '9':
                    chars[i] = 'N';
                    break;
                case '-':
                    chars[i] = 'L';
                    break;
                case ':':
                    chars[i] = 'D';
                    break;
                case ' ':
                    chars[i] = 'B';
                    break;
            }
        }
        return new String(chars);
    }

    private static String getApiChars(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case '0':
                    chars[i] = 'L';
                    break;
                case '1':
                    chars[i] = 'l';
                    break;
                case '2':
                    chars[i] = 'V';
                    break;
                case '3':
                    chars[i] = 'v';
                    break;
                case '4':
                    chars[i] = 'R';
                    break;
                case '5':
                    chars[i] = 'r';
                    break;
                case '6':
                    chars[i] = 'Y';
                    break;
                case '7':
                    chars[i] = 'y';
                    break;
                case '8':
                    chars[i] = 'P';
                    break;
                case '9':
                    chars[i] = 'I';
                    break;
                case '-':
                    chars[i] = 'W';
                    break;
                case ':':
                    chars[i] = 'w';
                    break;
                case ' ':
                    chars[i] = 'Q';
                    break;
            }
        }
        return new String(chars);
    }

    private static String getDataChars(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case '0':
                    chars[i] = 'P';
                    break;
                case '1':
                    chars[i] = 'O';
                    break;
                case '2':
                    chars[i] = 'I';
                    break;
                case '3':
                    chars[i] = 'U';
                    break;
                case '4':
                    chars[i] = 'Y';
                    break;
                case '5':
                    chars[i] = 'T';
                    break;
                case '6':
                    chars[i] = 'R';
                    break;
                case '7':
                    chars[i] = 'E';
                    break;
                case '8':
                    chars[i] = 'W';
                    break;
                case '9':
                    chars[i] = 'Q';
                    break;
                case '-':
                    chars[i] = 'A';
                    break;
                case ':':
                    chars[i] = 'S';
                    break;
                case ' ':
                    chars[i] = 's';
                    break;
            }
        }
        return new String(chars);
    }

    private static String getSuperChars(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case '0':
                    chars[i] = 'M';
                    break;
                case '1':
                    chars[i] = 'N';
                    break;
                case '2':
                    chars[i] = 'B';
                    break;
                case '3':
                    chars[i] = 'V';
                    break;
                case '4':
                    chars[i] = 'C';
                    break;
                case '5':
                    chars[i] = 'X';
                    break;
                case '6':
                    chars[i] = 'Z';
                    break;
                case '7':
                    chars[i] = 'L';
                    break;
                case '8':
                    chars[i] = 'K';
                    break;
                case '9':
                    chars[i] = 'J';
                    break;
                case '-':
                    chars[i] = 'H';
                    break;
                case ':':
                    chars[i] = 'G';
                    break;
                case ' ':
                    chars[i] = 'F';
                    break;
            }
        }
        return new String(chars);
    }


    public static String Signtimes() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String ApiSigntimes() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy-HH dd-mm-ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String DataSigntimes() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:MM-yyyy:::dd-SSS-mm-ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(TimeUtils.getNowMills());
        return str;
    }

    public static String DataSupertimes() {
        SimpleDateFormat formatter = new SimpleDateFormat("mm-dd-SSS-ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String times() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

}
