package com.example.todoandnote.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    private Calendar cal = Calendar.getInstance();;

    public DateTime()
    {}

    public DateTime(long millis){

        cal.setTimeInMillis(millis);
    }

    public int getDay()
    {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getYear()
    {
        return cal.get(Calendar.YEAR);
    }

    public int getMonth()
    {
        return cal.get(Calendar.MONTH);
    }

    public int getHour()
    {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute()
    {
        return cal.get(Calendar.MINUTE);
    }

    public static String formatDate(long millis)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-EEE-yyyy");
        return formatter.format(new Date(millis));
    }

    public static String getTwelveHourFormatted(int hour, int minute)
    {
        StringBuilder time = new StringBuilder();
        if(hour >= 13)
        {
            time.append(hour-12);
        }else{
            time.append(hour);
        }

        if(minute < 10)
        {
            time.append(" : 0"+minute);
        }else{
            time.append(" : "+minute);
        }
        if(hour >=13)
        {
            time.append(" PM");
        }else
        {
            time.append(" AM");
        }
        return time.toString();
    }

}
