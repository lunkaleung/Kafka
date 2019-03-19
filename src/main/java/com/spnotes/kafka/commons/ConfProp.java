package com.spnotes.kafka.commons;

import java.io.IOException;
import java.io.InputStream;

import java.util.logging.*;
import java.util.Properties;

public class ConfProp {
    private static Properties prop = null;
    private static Logger logger = Logger.getLogger(ConfProp.class.getSimpleName());

    public static Properties getInstance() {        
        try {
            prop = new Properties();
            InputStream inputStream = ConfProp.class.getResourceAsStream("/config/config.properties");
            prop.load(inputStream);
        } catch(IOException ex) {
            // logger.error("Failed to load configuration file", ex);
            logger.log(Level.WARNING, "Failed to load configuration file:" + ex);
        }

        return prop;
    }
}
