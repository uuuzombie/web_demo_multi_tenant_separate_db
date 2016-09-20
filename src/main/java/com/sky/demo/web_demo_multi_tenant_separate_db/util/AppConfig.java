package com.sky.demo.web_demo_multi_tenant_separate_db.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by user on 16/9/20.
 */
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private static final Locale locale = new Locale("zh");

    private static ResourceBundle appConfig = null;

    static {
        try {
            appConfig = ResourceBundle.getBundle("app_config", locale);
        } catch (Exception e) {
            logger.error("load app config error", e);
        }
    }


    public static String getItem(String key, String defaultValue) {
        String value = null;
        if (appConfig != null) {
            try {
                value = appConfig.getString(key.trim());
            } catch (Exception e) {
                logger.error("get value error", e);
            }
        }

        if (value == null) {
            value = defaultValue;
        }
        return value.trim();
    }

    public static String getItem(String key) {
        return getItem(key, null);
    }
}
