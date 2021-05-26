package sample.utils;

import java.io.*;
import java.util.Properties;

import cn.hutool.system.SystemPropsKeys;
import cn.hutool.system.SystemUtil;

public class ConfigurationUtils {
    private static String NAME="configuration.properties";
    private static Properties prop = new Properties();
    private static FileOutputStream oFile;

    static {
        InputStream in = null;
        try {
            String full_path = "";
            String currentDirPath = SystemUtil.get(SystemPropsKeys.CLASS_PATH);
            if (!currentDirPath.contains(";")) {
                full_path = currentDirPath.substring(0, currentDirPath.lastIndexOf("\\")) + "\\" + NAME;
                File file = new File(full_path);
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in = new FileInputStream(NAME);
                oFile = new FileOutputStream(NAME, true);//此处追加打开
                prop.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //根据key读取value
    public static String readeProperties(String key, String defaultValue) {
        if (prop.isEmpty()) {
            return defaultValue;
        }
        String value = prop.getProperty(key);
        return value == null ? defaultValue : value;
    }

    //写入properties信息
    public static void writeProperties(String parameterName, Object parameterValue) {
        try {
            prop.setProperty(parameterName, parameterValue.toString());
            prop.store(oFile, parameterName);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}