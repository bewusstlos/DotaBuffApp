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
    private String url;
    private String htmlSrc;
    private String profileName;
    private String avatarSrc;
    private String winRate;
    private String recordWins;
    private String recordLosses;
    private String recordAbandons;
    private String lastMatch;

    public Profile(String s) {
        url = s;
        setHtmlSrc(url);
        setProfileName();
        setAvatarSrc();
        setWinRate();
        setRecord();
        setLastMatch();
    }

    public String getUrl() {
        return url;
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
        Matcher m = Pattern.compile("<time datetime=\"(.*?)T(.*?):(.*?):.*?\"").matcher(htmlSrc);
        m.find();
        lastMatch = m.group(1) + " " + (Integer.parseInt(m.group(2)) + 3) +":"+ m.group(3);
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
    /*Мій клас для дати, який порівнює дату
     *дату останньої гри з поточною датою
     */
}
