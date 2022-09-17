package com.codifi.cp2.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DDMMYYYYHHMM = "dd/MM/yyyy HH:mm a";

    public static String parseDate(Date date) {
        String date2 = null;
        DateFormat formatter;
        formatter = new SimpleDateFormat("dd-MMM-yy");
        date2 = (String) formatter.format(date);
        return date2;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    // public static Date parseString(String dateString, DateFormat format) {
    // Date date = null;
    // if (StringUtil.isNotNullOrEmpty(dateString) && format != null) {
    // date = (Date) format.parse(dateString);
    // }
    // return date;
    // }

    public static String formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return formatDateByformat(date, dateFormat);
    }

    public static String formatDateByformat(Date date, DateFormat dateFormat) {
        String dateStr = null;
        try {
            if (date != null) {
                dateStr = dateFormat.format(date);
            }
        } catch (Exception e) {
            return null;
        }
        return dateStr;
    }

    /**
     * Method to get Epoch time on time stamp
     * 
     * @author Pradeep Ravichandran
     * @param createdOn
     * @return
     * @throws ParseException
     */
    public static String getEpochTimeFromDate(Date createdOn) throws ParseException {
        String StringDate = formatDate(createdOn, YYYYMMDDHHMMSS);
        SimpleDateFormat formatter1 = new SimpleDateFormat(YYYYMMDDHHMMSS);
        if (StringUtil.isNotNullOrEmpty(StringDate)) {
            Date date = (Date) formatter1.parse((StringDate));
            long epoch = date.getTime();
            return String.valueOf(epoch);
        } else {
            return "";
        }
    }
}