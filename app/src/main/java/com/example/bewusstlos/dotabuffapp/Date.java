package com.example.bewusstlos.dotabuffapp;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bewus on 4/10/2016.
 */
public class Date {
    int year;
    int month;
    int day;
    int hour;
    int minute;

    public Date(String htmlSrc) {
        Matcher m = Pattern.compile("<time datetime=\"(.*?)-(.*?)-(.*?)T(.*?):(.*?):(.*?)+(.*?):(.*?)\"").matcher(htmlSrc);
        m.find();
        year = Integer.parseInt(m.group(1));
        month = Integer.parseInt(m.group(2));
        day = Integer.parseInt(m.group(3));
        hour = Integer.parseInt(m.group(4));
        minute = Integer.parseInt(m.group(5));
    }

    public String compareWithCurrent() {
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.YEAR) > this.year)
            return (c.get(Calendar.YEAR) - this.year) + " years ago";
        if (c.get(Calendar.MONTH) > this.month)
            return (c.get(Calendar.MONTH) - this.month) + " months ago";
        if (c.get(Calendar.DATE) > this.day)
            return (c.get(Calendar.DATE) - this.day) + " days ago";
        if (c.get(Calendar.HOUR) > this.hour)
            return (c.get(Calendar.HOUR) - this.hour) + " hours ago";
        if (c.get(Calendar.MINUTE) > this.minute)
            return (c.get(Calendar.MINUTE) - this.minute) + " minutes ago";
        return "Playing";
    }
}
