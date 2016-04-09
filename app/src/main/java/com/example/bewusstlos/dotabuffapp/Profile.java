package com.example.bewusstlos.dotabuffapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bewus on 4/9/2016.
 */
public class Profile {
    private String htmlSrc;
    private String profileName;
    private String avatarSrc;
    private String winRate;
    private String recordWins;
    private String recordLosses;
    private String recordAbandons;
    private String lastMatch;

    public Profile(){
        String url = "http://www.dotabuff.com/players/194389503";
        setHtmlSrc(url);
        setProfileName();
        setAvatarSrc();
        setWinRate();
        setRecord();
        setLastMatch();
    }
    private void setProfileName(){
        Matcher m = Pattern.compile("<div class=\"header-content-title\"><h1>(.*?)<small>Overview</small></h1>").matcher(htmlSrc);
        m.find();
        profileName = m.group(1);
    }

    public String getProfileName(){
        return profileName;
    }
    private void setAvatarSrc(){
        Matcher m = Pattern.compile("<meta property=\"og:image\" content=\"(.*?)\" />").matcher(htmlSrc);
        m.find();
        avatarSrc = m.group(1);
    }

    public String getAvatarSrc(){
        return avatarSrc;
    }

    private void setWinRate(){
        Matcher m = Pattern.compile("<dt>Record</dt></dl><dl><dd>(.*?)</dd><dt>Win Rate</dt></dl>").matcher(htmlSrc);
        m.find();
        winRate = m.group(1);
    }

    public String getWinRate(){
        return winRate;
    }

    private void setRecord(){
        Matcher m = Pattern.compile("<span class=\"game-record\"><span class=\"wins\">(.*?)</span>" +
                "<span class=\"sep\">-</span><span class=\"losses\">(.*?)</span>" +
                "<span class=\"sep\">-</span><span class=\"abandons\">(.*?)</span>").matcher(htmlSrc);
        m.find();
        recordWins = m.group(1);
        recordLosses = m.group(2);
        recordAbandons = m.group(3);
    }

    public String getRecordWins(){
        return recordWins;
    }

    public String getRecordLosses(){
        return recordLosses;
    }

    public String getRecordAbandons(){
        return recordAbandons;
    }
    private void setLastMatch(){
        //data-time-ago="2016-04-09T09:25:25+00:00"
        Date date = new Date();
        lastMatch = date.compareWithCurrent();
    }

    public String getLastMatch(){
        return lastMatch;
    }
    private void setHtmlSrc(String url){
        RequestTask requestTask = new RequestTask();
        requestTask.execute(url);
        try {
            htmlSrc = requestTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("Interrupt!","");
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.i("Execution!","");
        }
    }
    /*Мій клас для дати, який ппорівнює дату
     *дату останньої гри з поточною датою
     */
    class Date{
        int year;
        int month;
        int day;
        int hour;
        int minute;

        public Date(){
            Matcher m = Pattern.compile("<dd><time datetime=\"(.*?)-(.*?)-(.*?)T(.*?):(.*?):(.*?)+(.*?):(.*?)\" title").matcher(htmlSrc);
            m.find();
            year = Integer.parseInt(m.group(1));
            month = Integer.parseInt(m.group(2));
            day = Integer.parseInt(m.group(3));
            hour = Integer.parseInt(m.group(4));
            minute = Integer.parseInt(m.group(5));
        }
        public String compareWithCurrent(){
            java.util.Date date = new java.util.Date();
            date.setHours(date.getDay() + 7);
            if(date.getYear() > this.year)
                return (date.getYear() - this.year) + " years ago";
            if(date.getMonth() > this.month)
                return (date.getMonth() - this.month) + " months ago";
            if(date.getDay() > this.day)
                return (date.getDay() - this.day) + " days ago";
            if(date.getHours() > this.hour)
                return (date.getHours() - this.hour) + " hours ago";
            if(date.getMinutes()> this.minute)
                return (date.getMinutes() - this.minute) + " minutes ago";
            return "Playing";
        }
    }
    //Клас для завантаження коду сторінки
    class RequestTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... url) {
            StringBuilder sb = new StringBuilder();
            try {
                URL pageURL = new URL(url[0]);
                String inputLine;
                URLConnection uc = pageURL.openConnection();
                BufferedReader buff = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                while ((inputLine = buff.readLine()) != null) {
                    sb.append(inputLine);
                }
            }
            catch (Exception e) {
            }
            return sb.toString();
        }
    }
}
