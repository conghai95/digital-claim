package com.project.dco_common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static Date toDateFromString(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String toStringFromDate(Date date, String format) {
        try {
            DateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static Date getCurrentDate(String format) {
        return new Date();
    }

    public static String getCurrentDateString(String format) {
        return toStringFromDate(getCurrentDate(format), format);
    }

    public static Date addMinutesToDate(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date addDayToDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    /**
     * compare two date
     * @param dateOne
     * @param dateTwo
     * @return return 1 in case dateOne > dateTwo, return -1 in case dateOne < dateTwo else return 0
     */
    public static int compareDate(Date dateOne, Date dateTwo) {
        Calendar calendarOne = Calendar.getInstance();
        Calendar calendarTwo = Calendar.getInstance();
        calendarOne.setTime(dateOne);
        calendarTwo.setTime(dateTwo);
        return calendarOne.compareTo(calendarTwo);
    }
}
