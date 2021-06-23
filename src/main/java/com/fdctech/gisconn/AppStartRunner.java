package com.fdctech.gisconn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.TimeZone;

@Component
public class AppStartRunner implements ApplicationRunner {
    private static final Logger logger = LogManager.getLogger(AppStartRunner.class);

    @Override
    public void run(ApplicationArguments args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+5"));
        logger.info("Set UTC TimeZone");

        Locale.setDefault(Locale.ENGLISH);

        logger.info("set EN locale");
        logger.info("init order storage");
        logger.info("gisconn started the party");
    }
}
