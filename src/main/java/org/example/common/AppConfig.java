package org.example.common;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(AppConfig.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String name) {
        return properties.getProperty(name);
    }
}
