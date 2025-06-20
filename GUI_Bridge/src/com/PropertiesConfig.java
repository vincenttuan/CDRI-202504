package com;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


public class PropertiesConfig {

    public static Properties PROP;
    
    public static void makePropertiesConfig() {
        PROP = new Properties();
        String configFileName = "config_bridge.properties"; // 預設：正式版

        // 輸出當前使用的配置文件路徑
        String configFilePath = "./" + configFileName;
        //Log.printMsg(configFilePath, PropertiesConfig.class);
        //System.out.println(configFilePath);
        
        // 使用 FileInputStream 讀取本地文件系統中的文件
        try (FileInputStream input = new FileInputStream(configFilePath);
             InputStreamReader reader = new InputStreamReader(input, "UTF-8")) {
            PROP.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
