package com.restendpoints.musicapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static String getTodaysDate(){
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, Locale.US);
        String today = dateFormat.format(new Date());
        logger.info("Retrieved today's date: {}", today);
        return today;
    }
}
