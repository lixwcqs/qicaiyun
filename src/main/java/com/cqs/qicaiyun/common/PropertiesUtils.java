package com.cqs.qicaiyun.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Created by cqs on 2017/11/9.
 */
public class PropertiesUtils {

    //配置文件路径；
    private static String path = "config.properties";

    public static String getProperties(String properties) {
        InputStream is = ClassLoader.getSystemResourceAsStream(path);
        Properties prop = new Properties();
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return prop.getProperty(properties);
    }

}
