package com.eup.rxandroidtimedemo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static SimpleDateFormat getTimeFormat() {
        return new SimpleDateFormat("hh:mm:ss");
    }

    public static String getCurrentTime() {
        Date currentDate = Calendar.getInstance().getTime();
        return getTimeFormat().format(currentDate);
    }
}
