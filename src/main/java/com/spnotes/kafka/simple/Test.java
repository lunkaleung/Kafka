package com.spnotes.kafka.simple;

import com.spnotes.kafka.commons.*;
import java.util.logging.*;

public class Test {
    public static void main(String[] argv) {
        try {
            FileHandler file = new FileHandler("/home/ubuntu/123.txt");
            file.setFormatter(new LogFormatter());
            
            // file.setFilter(new LogFilter());
            // System.out.println(ConfProp.getInstance().getProperty("a"));

            // ConsoleHandler console = new ConsoleHandler();
            // console.setFormatter(new LogFormatter());

            Logger logger = Logger.getLogger("Test");
            //logger.addHandler(console);
            logger.addHandler(file); 

            logger.log(Level.INFO, "Config data");

        } catch (Exception e) {

        }
    }
}