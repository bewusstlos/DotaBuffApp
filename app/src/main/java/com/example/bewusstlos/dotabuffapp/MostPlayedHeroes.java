package com.example.bewusstlos.dotabuffapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bewus on 4/10/2016.
 */
public class MostPlayedHeroes {
    ArrayList<String> info;
    ArrayList<GameOfMostPlayedHeroes> gameOfMostPlayedHeroes;
    private String htmlSrc;

    public MostPlayedHeroes() {
        info = new ArrayList<String>();
        setHtmlSrc();
        gameOfMostPlayedHeroes = new ArrayList<GameOfMostPlayedHeroes>();
        for (int i = 0; i < 10; i++)
            gameOfMostPlayedHeroes.add(new GameOfMostPlayedHeroes(i));
    }

    private void setHtmlSrc() {
        RequestTask requestTask = new RequestTask();
        requestTask.execute(Profile.getUrl());
        try {
            htmlSrc = requestTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("Interrupt!", "");
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.i("Execution!", "");
        }
        Matcher m = Pattern.compile("<div class=\"r-row\"(.*?)<div class=\"clearfix\"></div></div>").matcher(htmlSrc);
        for (int i = 0; i < 10; i++) {
            m.find();
            info.add(m.group(1));
        }
    }

    class GameOfMostPlayedHeroes {
        private String heroName;
        private String lastMatch;
        private String matchesPlayed;
        private String WinRate;
        private float kda;

        public GameOfMostPlayedHeroes(int index) {
            setHeroName(index);
        }

        private void setHeroName(int index) {
            //Нада пофіксить
            Matcher m = Pattern.compile("data-link-to=\".*?\">" +
                    "<div class=\"r-fluid r-40 r-icon-text\"><div class=\"r-label r-always-hidden\">Hero</div><div class=\"r-body\">" +
                    "<div class=\"image-container image-container-hero image-container-icon\"><a href=\".*?\"><img class=\"image-hero image-icon\" rel=\"tooltip-remote\" " +
                    "title=\".*?\" data-tooltip-url=\".*?\" src=\".*?\" /></a></div>" +
                    "<div class=\"r-none-mobile\"><a href=\".*?\">(.*?)</a><div class=\"subtext minor\"><a href=\".*?\"><time datetime=\".*?\" title=\".*?\" data-time-ago=\".*?\">.*?</time></a></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\"><div class=\"r-label\">Matches Played</div><div class=\"r-body\">.*?<div class=\"bar bar-default\"><div class=\"segment segment-match\" style=\"width: 100.0%;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\"><div class=\"r-label\">Win Rate</div><div class=\"r-body\">.*?<div class=\"bar bar-default\"><div class=\"segment segment-win\" style=\"width: 63.265304351020426%;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph r-none-mobile\"><div class=\"r-label\">KDA Ratio</div><div class=\"r-body\">.*?<div class=\"bar bar-default\"><div class=\"segment segment-kda\" style=\"width: 47.049414859453954%;\"></div></div></div></div>").matcher(info.get(index));
            m.find();
            heroName = m.group(1);
        }

        private void setLastMatch(int index) {

        }

        private void setMatchesPlayed() {

        }

        private void setWinRate() {

        }

        private void setKda() {

        }
    }
}
