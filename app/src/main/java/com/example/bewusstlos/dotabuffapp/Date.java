package com.example.bewusstlos.dotabuffapp;

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
        Matcher m = Pattern.compile("<time datetime=\"(.*?)-(.*?)-(.*?)T(.*?):(.*?):(.*?)+(.*?):(.*?)\" title").matcher(htmlSrc);
        m.find();
        year = Integer.parseInt(m.group(1));
        month = Integer.parseInt(m.group(2));
        day = Integer.parseInt(m.group(3));
        hour = Integer.parseInt(m.group(4));
        minute = Integer.parseInt(m.group(5));
    }

    public String compareWithCurrent() {
        java.util.Date date = new java.util.Date();
        date.setHours(date.getDay() + 7);
        if (date.getYear() > this.year)
            return (date.getYear() - this.year) + " years ago";
        if (date.getMonth() > this.month)
            return (date.getMonth() - this.month) + " months ago";
        if (date.getDay() > this.day)
            return (date.getDay() - this.day) + " days ago";
        if (date.getHours() > this.hour)
            return (date.getHours() - this.hour) + " hours ago";
        if (date.getMinutes() > this.minute)
            return (date.getMinutes() - this.minute) + " minutes ago";
        return "Playing";
    }
}
