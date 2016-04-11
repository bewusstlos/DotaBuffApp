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
    String url;
    ArrayList<String> info;
    ArrayList<GameOfMostPlayedHeroes> gameOfMostPlayedHeroes;
    private String htmlSrc;

    public MostPlayedHeroes(String url) {
        this.url = url;
        info = new ArrayList<String>();
        setHtmlSrc();
        gameOfMostPlayedHeroes = new ArrayList<GameOfMostPlayedHeroes>();
        for (int i = 0; i < 10; i++)
            gameOfMostPlayedHeroes.add(new GameOfMostPlayedHeroes(i));
    }

    public GameOfMostPlayedHeroes getGameOfMostPlayedHeroes(int index) {
        return gameOfMostPlayedHeroes.get(index);
    }

    private void setHtmlSrc() {
        RequestTask requestTask = new RequestTask();
        requestTask.execute(this.url);
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
        private String heroAvatarSrc;
        private String matchesPlayed;
        private String winRate;
        private String kda;

        public GameOfMostPlayedHeroes(int index) {
            setHeroName(index);
            setLastMatch(index);
            setHeroAvatarSrc(index);
            setMatchesPlayed(index);
            setWinRate(index);
            setKda(index);
        }

        public String getHeroName() {
            return heroName;
        }

        private void setHeroName(int index) {
            //Нада пофіксить
            Matcher m = Pattern.compile("data-link-to=\".*?\">" +
                    "<div class=\"r-fluid r-40 r-icon-text\">" +
                    "<div class=\"r-label r-always-hidden\">Hero</div><div class=\"r-body\">" +
                    "<div class=\"image-container image-container-hero image-container-icon\">" +
                    "<a href=\".*?\">" +
                    "<img class=\"image-hero image-icon\" rel=\"tooltip-remote\" title=\"(.*?)\" " +
                    "data-tooltip-url=\".*?\" " +
                    "src=\".*?\" /></a></div>" +
                    "<div class=\"r-none-mobile\">" +
                    "<a href=\".*?\">.*?</a>" +
                    "<div class=\"subtext minor\"><a href=\".*?\">" +
                    "<time datetime=\".*?\" title=\".*?\" " +
                    "data-time-ago=\".*?\">.*?</time></a></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\">" +
                    "<div class=\"r-label\">Matches Played</div><div class=\"r-body\">.*?<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-match\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\"><div class=\"r-label\">Win Rate</div>" +
                    "<div class=\"r-body\">.*?<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-win\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph r-none-mobile\"><div class=\"r-label\">KDA Ratio</div>" +
                    "<div class=\"r-body\">.*?<div class=\"bar bar-default\"><div class=\"segment segment-kda\" style=\"width: .*?;\">" +
                    "</div></div></div></div>").matcher(info.get(index));
            m.find();
            heroName = m.group(1);
        }

        public String getLastMatch() {
            return lastMatch;
        }

        private void setLastMatch(int index) {
            Matcher m = Pattern.compile("data-link-to=\".*?\">" +
                    "<div class=\"r-fluid r-40 r-icon-text\">" +
                    "<div class=\"r-label r-always-hidden\">Hero</div><div class=\"r-body\">" +
                    "<div class=\"image-container image-container-hero image-container-icon\">" +
                    "<a href=\".*?\">" +
                    "<img class=\"image-hero image-icon\" rel=\"tooltip-remote\" title=\".*?\" " +
                    "data-tooltip-url=\".*?\" " +
                    "src=\".*?\" /></a></div>" +
                    "<div class=\"r-none-mobile\">" +
                    "<a href=\".*?\">.*?</a>" +
                    "<div class=\"subtext minor\"><a href=\".*?\">" +
                    "(.*?) title=\".*?\" " +
                    "data-time-ago=\".*?\">.*?</time></a></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\">" +
                    "<div class=\"r-label\">Matches Played</div><div class=\"r-body\">.*?<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-match\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\"><div class=\"r-label\">Win Rate</div>" +
                    "<div class=\"r-body\">.*?<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-win\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph r-none-mobile\"><div class=\"r-label\">KDA Ratio</div>" +
                    "<div class=\"r-body\">.*?<div class=\"bar bar-default\"><div class=\"segment segment-kda\" style=\"width: .*?;\">" +
                    "</div></div></div></div>").matcher(info.get(index));
            m.find();
            String unparsedDate = m.group(1);
            Date date = new Date(unparsedDate);
            lastMatch = date.compareWithCurrent();
        }

        public String getHeroAvatarSrc() {
            return heroAvatarSrc;
        }

        private void setHeroAvatarSrc(int index) {
            Matcher m = Pattern.compile("data-link-to=\".*?\">" +
                    "<div class=\"r-fluid r-40 r-icon-text\">" +
                    "<div class=\"r-label r-always-hidden\">Hero</div><div class=\"r-body\">" +
                    "<div class=\"image-container image-container-hero image-container-icon\">" +
                    "<a href=\".*?\">" +
                    "<img class=\"image-hero image-icon\" rel=\"tooltip-remote\" title=\".*?\" " +
                    "data-tooltip-url=\".*?\" " +
                    "src=\"(.*?)\" /></a></div>" +
                    "<div class=\"r-none-mobile\">" +
                    "<a href=\".*?\">.*?</a>" +
                    "<div class=\"subtext minor\"><a href=\".*?\">" +
                    ".*? title=\".*?\" " +
                    "data-time-ago=\".*?\">.*?</time></a></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\">" +
                    "<div class=\"r-label\">Matches Played</div><div class=\"r-body\">.*?<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-match\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\"><div class=\"r-label\">Win Rate</div>" +
                    "<div class=\"r-body\">.*?<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-win\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph r-none-mobile\"><div class=\"r-label\">KDA Ratio</div>" +
                    "<div class=\"r-body\">.*?<div class=\"bar bar-default\"><div class=\"segment segment-kda\" style=\"width: .*?;\">" +
                    "</div></div></div></div>").matcher(info.get(index));
            m.find();
            heroAvatarSrc = "http://www.dotabuff.com" + m.group(1);
        }

        public String getMatchesPlayed() {
            return matchesPlayed;
        }

        private void setMatchesPlayed(int index) {
            Matcher m = Pattern.compile("data-link-to=\".*?\">" +
                    "<div class=\"r-fluid r-40 r-icon-text\">" +
                    "<div class=\"r-label r-always-hidden\">Hero</div><div class=\"r-body\">" +
                    "<div class=\"image-container image-container-hero image-container-icon\">" +
                    "<a href=\".*?\">" +
                    "<img class=\"image-hero image-icon\" rel=\"tooltip-remote\" title=\".*?\" " +
                    "data-tooltip-url=\".*?\" " +
                    "src=\".*?\" /></a></div>" +
                    "<div class=\"r-none-mobile\">" +
                    "<a href=\".*?\">.*?</a>" +
                    "<div class=\"subtext minor\"><a href=\".*?\">" +
                    ".*? title=\".*?\" " +
                    "data-time-ago=\".*?\">.*?</time></a></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\">" +
                    "<div class=\"r-label\">Matches Played</div><div class=\"r-body\">(.*?)<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-match\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\"><div class=\"r-label\">Win Rate</div>" +
                    "<div class=\"r-body\">.*?<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-win\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph r-none-mobile\"><div class=\"r-label\">KDA Ratio</div>" +
                    "<div class=\"r-body\">.*?<div class=\"bar bar-default\"><div class=\"segment segment-kda\" style=\"width: .*?;\">" +
                    "</div></div></div></div>").matcher(info.get(index));
            m.find();
            matchesPlayed = m.group(1);
        }

        public String getWinRate() {
            return winRate;
        }

        private void setWinRate(int index) {
            Matcher m = Pattern.compile("data-link-to=\".*?\">" +
                    "<div class=\"r-fluid r-40 r-icon-text\">" +
                    "<div class=\"r-label r-always-hidden\">Hero</div><div class=\"r-body\">" +
                    "<div class=\"image-container image-container-hero image-container-icon\">" +
                    "<a href=\".*?\">" +
                    "<img class=\"image-hero image-icon\" rel=\"tooltip-remote\" title=\".*?\" " +
                    "data-tooltip-url=\".*?\" " +
                    "src=\".*?\" /></a></div>" +
                    "<div class=\"r-none-mobile\">" +
                    "<a href=\".*?\">.*?</a>" +
                    "<div class=\"subtext minor\"><a href=\".*?\">" +
                    ".*? title=\".*?\" " +
                    "data-time-ago=\".*?\">.*?</time></a></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\">" +
                    "<div class=\"r-label\">Matches Played</div><div class=\"r-body\">.*?<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-match\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\"><div class=\"r-label\">Win Rate</div>" +
                    "<div class=\"r-body\">(.*?)<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-win\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph r-none-mobile\"><div class=\"r-label\">KDA Ratio</div>" +
                    "<div class=\"r-body\">.*?<div class=\"bar bar-default\"><div class=\"segment segment-kda\" style=\"width: .*?;\">" +
                    "</div></div></div></div>").matcher(info.get(index));
            m.find();
            winRate = m.group(1);
        }

        public String getKda() {
            return kda;
        }

        private void setKda(int index) {
            Matcher m = Pattern.compile("data-link-to=\".*?\">" +
                    "<div class=\"r-fluid r-40 r-icon-text\">" +
                    "<div class=\"r-label r-always-hidden\">Hero</div><div class=\"r-body\">" +
                    "<div class=\"image-container image-container-hero image-container-icon\">" +
                    "<a href=\".*?\">" +
                    "<img class=\"image-hero image-icon\" rel=\"tooltip-remote\" title=\".*?\" " +
                    "data-tooltip-url=\".*?\" " +
                    "src=\".*?\" /></a></div>" +
                    "<div class=\"r-none-mobile\">" +
                    "<a href=\".*?\">.*?</a>" +
                    "<div class=\"subtext minor\"><a href=\".*?\">" +
                    ".*? title=\".*?\" " +
                    "data-time-ago=\".*?\">.*?</time></a></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\">" +
                    "<div class=\"r-label\">Matches Played</div><div class=\"r-body\">.*?<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-match\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph\"><div class=\"r-label\">Win Rate</div>" +
                    "<div class=\"r-body\">.*?<div class=\"bar bar-default\">" +
                    "<div class=\"segment segment-win\" style=\"width: .*?;\"></div></div></div></div>" +
                    "<div class=\"r-fluid r-20 r-line-graph r-none-mobile\"><div class=\"r-label\">KDA Ratio</div>" +
                    "<div class=\"r-body\">(.*?)<div class=\"bar bar-default\"><div class=\"segment segment-kda\" style=\"width: .*?;\">" +
                    "</div></div></div></div>").matcher(info.get(index));
            m.find();
            kda = m.group(1);
        }
    }
}
