package dev.samoilov.artur.remindmeapp;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {

    public static String getDate(long date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

    public static String getTime(long time){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(time);
    }

    public static String getFullDate(long time){
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd.MM.yy  HH:mm");
        return timeFormat.format(time);
    }
}
