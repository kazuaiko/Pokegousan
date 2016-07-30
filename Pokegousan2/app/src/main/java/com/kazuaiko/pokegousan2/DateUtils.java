package com.kazuaiko.pokegousan2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by naitou_ka on 2016/07/29.
 */
public class DateUtils {

    public static Date fromString(String format, String string) throws ParseException {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format, Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        dateFormatter.setCalendar(new GregorianCalendar());
        return dateFormatter.parse(string);
    }
}