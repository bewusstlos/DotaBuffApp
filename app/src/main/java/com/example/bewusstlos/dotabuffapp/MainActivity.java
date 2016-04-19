package com.example.bewusstlos.dotabuffapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends StartActivity {
    String url;
    LinearLayout matchesLayout;
    LinearLayout overviewLayout;
    LinearLayout heroesLayout;
    String htmlSrcMatches = null;

    @Override
    /*Встановлюється шапка додатку
     *з основною інформацією
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        url = b.getString("Link");
        RequestTask requestTask = new RequestTask();
        requestTask.execute(url + "/matches");
        try {
            htmlSrcMatches = requestTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("Interrupt!", "");
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.i("Execution!", "");
        }
        matchesLayout = (LinearLayout) findViewById(R.id.matches_layout);
        overviewLayout = (LinearLayout) findViewById(R.id.overview_layout);
        heroesLayout = (LinearLayout) findViewById(R.id.heroes);
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.overview);
        spec.setIndicator("Overview");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.matches);
        spec.setIndicator("Matches");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.heroes);
        spec.setIndicator("Heroes");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.items);
        spec.setIndicator("Items");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag4");
        spec.setContent(R.id.records);
        spec.setIndicator("Records");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag5");
        spec.setContent(R.id.scenarios);
        spec.setIndicator("Scenarios");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag6");
        spec.setContent(R.id.activity);
        spec.setIndicator("Activity");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag7");
        spec.setContent(R.id.trends);
        spec.setIndicator("Trends");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        ImageView imgProfileAvatar = (ImageView) findViewById(R.id.img_profile_avatar);
        TextView txtProfileName = (TextView) findViewById(R.id.txt_profile_name);
        TextView txtWinRate = (TextView) findViewById(R.id.txt_win_rate);
        TextView txtRecordWins = (TextView) findViewById(R.id.txt_record_wins);
        TextView txtRecordLosses = (TextView) findViewById(R.id.txt_record_losses);
        TextView txtRecordAbandons = (TextView) findViewById(R.id.txt_record_abandons);
        TextView txtLastMatch = (TextView) findViewById(R.id.txt_last_match);
        try {
            Profile profile = new Profile(url);
            new DownloadImageTask(imgProfileAvatar).execute(profile.getAvatarSrc());
            txtProfileName.setText(profile.getProfileName());
            txtWinRate.setText(profile.getWinRate());
            txtRecordWins.setText(profile.getRecordWins());
            txtRecordLosses.setText(profile.getRecordLosses());
            txtRecordAbandons.setText(profile.getRecordAbandons());
            txtLastMatch.setText(profile.getLastMatch());
            setOverviewLayout(overviewLayout);
            setMatchesLayout(matchesLayout);
        } catch (IllegalStateException e) {
            Toast.makeText(this, "This profile is private", Toast.LENGTH_SHORT);
        }
    }

    public void setMatchesLayout(LinearLayout layout) {
        ArrayList<Matches> matches = new ArrayList<Matches>();
        for (int i = 0; i < 40; i++) {
            matches.add(i, new Matches(i));

            LinearLayout l = new LinearLayout(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(16, 16, 16, 0);
            l.setOrientation(LinearLayout.VERTICAL);

            RelativeLayout matchesL = new RelativeLayout(this);
            matchesL.setBackgroundColor(Color.rgb(55, 71, 79));
            matchesL.setPadding(8, 8, 8, 8);
            RelativeLayout.LayoutParams paramsForOverviewL = new RelativeLayout.LayoutParams
                    (
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                    );
            paramsForOverviewL.setMargins(16, 16, 16, 0);

            ImageView img = new ImageView(this);
            new DownloadImageTask(img).execute(matches.get(i).imgHeroSrc);
            RelativeLayout.LayoutParams paramsForImg = new RelativeLayout.LayoutParams
                    (
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
                    );
            img.setId(i);

            LinearLayout firstInnerMatchesL = new LinearLayout(this);
            firstInnerMatchesL.setOrientation(LinearLayout.VERTICAL);
            RelativeLayout.LayoutParams paramsForFirstInnerMatchesL = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //paramsForFirstInnerMatchesL.addRule(RelativeLayout.RIGHT_OF,img.getId());
            paramsForFirstInnerMatchesL.setMargins(150, 0, 16, 0);
            firstInnerMatchesL.setId(i + 40);

            TextView txtHeroName = new TextView(this);
            txtHeroName.setText(matches.get(i).heroName);
            txtHeroName.setTextColor(Color.WHITE);
            txtHeroName.setId(i + 80);
            firstInnerMatchesL.addView(txtHeroName);

            TextView txtMatchTime = new TextView(this);
            txtMatchTime.setText(matches.get(i).matchTime);
            txtMatchTime.setTextColor(Color.argb(170, 255, 255, 255));
            txtMatchTime.setId(i + 120);
            firstInnerMatchesL.addView(txtMatchTime);

            TextView txtMatchRanked = new TextView(this);
            txtMatchRanked.setText(matches.get(i).matchRanked);
            txtMatchRanked.setTextSize(14);
            txtMatchRanked.setTextColor(Color.argb(170, 255, 255, 255));
            RelativeLayout.LayoutParams paramsforMatchRanked = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsforMatchRanked.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            txtMatchRanked.setId(i + 160);

            TextView txtMatchType = new TextView(this);
            txtMatchType.setText(matches.get(i).matchType);
            txtMatchType.setTextSize(14);
            txtMatchType.setTextColor(Color.argb(170, 255, 255, 255));
            RelativeLayout.LayoutParams paramsForMatchType = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsForMatchType.addRule(RelativeLayout.BELOW, txtMatchRanked.getId());
            paramsForMatchType.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            txtMatchType.setId(i + 200);

            TextView txtResult = new TextView(this);
            RelativeLayout.LayoutParams paramsForTxtResult = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsForTxtResult.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            paramsForTxtResult.addRule(RelativeLayout.BELOW, firstInnerMatchesL.getId());
            txtResult.setTextColor(Color.argb(170, 255, 255, 255));
            txtResult.setTextSize(18);
            txtResult.setText(matches.get(i).result);
            txtResult.setTextColor(getResources().getColor(R.color.textSecondary));
            txtResult.setId(i + 280);
            matchesL.addView(txtResult, paramsForTxtResult);

            TextView txtDuration = new TextView(this);
            txtDuration.setText(matches.get(i).duration);
            RelativeLayout.LayoutParams paramsForTxtDuration = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsForTxtDuration.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            paramsForTxtDuration.addRule(RelativeLayout.BELOW, txtResult.getId());
            txtDuration.setTextColor(Color.argb(170, 255, 255, 255));
            txtDuration.setId(i + 320);

            matchesL.addView(txtDuration, paramsForTxtDuration);

            LinearLayout matchesProgressKdaL = new LinearLayout(this);
            RelativeLayout.LayoutParams paramsForMatchesProgressKdaL = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            matchesProgressKdaL.setOrientation(LinearLayout.HORIZONTAL);
            paramsForMatchesProgressKdaL.addRule(RelativeLayout.BELOW, txtDuration.getId());
            matchesProgressKdaL.setId(i + 360);

            RelativeLayout.LayoutParams paramsForTxtBar = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsForTxtBar.addRule(RelativeLayout.CENTER_IN_PARENT);

            LinearLayout killBar = new LinearLayout(this);
            killBar.setBackgroundColor(Color.argb(170, 230, 0, 0));
            float killWeight = ((matches.get(i).kill + matches.get(i).death + matches.get(i).assist) / 100) * matches.get(i).kill;
            LinearLayout.LayoutParams paramsForKillBar = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, killWeight);
            killBar.setId(i + 400);

            TextView txtKillBar = new TextView(this);
            txtKillBar.setText(Float.toString(matches.get(i).kill).replace(".0", ""));
            txtKillBar.setTextColor(Color.argb(170, 255, 255, 255));
            killBar.addView(txtKillBar, paramsForKillBar);

            LinearLayout deathBar = new LinearLayout(this);
            deathBar.setBackgroundColor(Color.argb(170, 180, 180, 180));
            float deathWeight = ((matches.get(i).kill + matches.get(i).death + matches.get(i).assist) / 100) * matches.get(i).death;
            LinearLayout.LayoutParams paramsForDeathBar = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, deathWeight);

            TextView txtDeathBar = new TextView(this);
            txtDeathBar.setText(Float.toString(matches.get(i).death).replace(".0", ""));
            txtDeathBar.setTextColor(Color.argb(170, 255, 255, 255));
            deathBar.addView(txtDeathBar, paramsForKillBar);

            LinearLayout assistBar = new LinearLayout(this);
            assistBar.setBackgroundColor(Color.argb(170, 0, 230, 0));
            float assistWeight = ((matches.get(i).kill + matches.get(i).death + matches.get(i).assist) / 100) * matches.get(i).assist;
            LinearLayout.LayoutParams paramsForAssistBar = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, assistWeight);

            TextView txtAssistBar = new TextView(this);
            txtAssistBar.setText(Float.toString(matches.get(i).assist).replace(".0", ""));
            txtAssistBar.setTextColor(Color.argb(170, 255, 255, 255));
            assistBar.addView(txtAssistBar, paramsForKillBar);

            LinearLayout thirdInnerMatchesL = new LinearLayout(this);
            thirdInnerMatchesL.setOrientation(LinearLayout.HORIZONTAL);
            RelativeLayout.LayoutParams paramsForThirdInnerMatchesL = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsForThirdInnerMatchesL.addRule(RelativeLayout.BELOW, matchesProgressKdaL.getId());
            paramsForThirdInnerMatchesL.addRule(RelativeLayout.CENTER_HORIZONTAL);
            paramsForThirdInnerMatchesL.setMargins(0, 16, 0, 0);

            for (int j = 0; j < 6; j++) {
                if (matches.get(i).itemsList[j] != null) {
                    ImageView imgItem = new ImageView(this);
                    RelativeLayout.LayoutParams imgLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    imgItem.setPadding(0, 0, 16, 0);
                    new DownloadImageTask(imgItem).execute(matches.get(i).itemsList[j]);
                    thirdInnerMatchesL.addView(imgItem, imgLP);
                }
            }

            matchesProgressKdaL.addView(killBar, paramsForKillBar);
            matchesProgressKdaL.addView(deathBar, paramsForDeathBar);
            matchesProgressKdaL.addView(assistBar, paramsForAssistBar);

            matchesL.addView(thirdInnerMatchesL, paramsForThirdInnerMatchesL);
            matchesL.addView(matchesProgressKdaL, paramsForMatchesProgressKdaL);
            matchesL.addView(txtMatchRanked, paramsforMatchRanked);
            matchesL.addView(txtMatchType, paramsForMatchType);
            matchesL.addView(img, paramsForImg);
            matchesL.addView(firstInnerMatchesL, paramsForFirstInnerMatchesL);
            l.addView(matchesL, paramsForOverviewL);
            //if (i != 0)
                layout.addView(l, lp);
        }
    }

    public void setOverviewLayout(LinearLayout layout) {
        MostPlayedHeroes mph = new MostPlayedHeroes(url);

        for (int i = 0; i < 10; i++) {
            LinearLayout l = new LinearLayout(this);
            l.setBackgroundColor(Color.rgb(55, 71, 79));
            l.setPadding(8, 8, 8, 8);
            l.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams paramsForL = new LinearLayout.LayoutParams
                    (
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
            paramsForL.setMargins(16, 16, 16, 0);

            RelativeLayout overviewL = new RelativeLayout(this);
            overviewL.setPadding(8, 8, 8, 8);
            RelativeLayout.LayoutParams paramsForOverviewL = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            ImageView img = new ImageView(this);
            new DownloadImageTask(img).execute(mph.getGameOfMostPlayedHeroes(i).getHeroAvatarSrc());
            RelativeLayout.LayoutParams paramsForImg = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsForImg.addRule(RelativeLayout.CENTER_VERTICAL);
            paramsForImg.setMargins(8, 8, 32, 8);
            img.setId(i);

            TextView txtHeroName = new TextView(this);
            RelativeLayout.LayoutParams paramsForTxtHeroName = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //paramsForTxtHeroName.addRule(RelativeLayout.RIGHT_OF, img.getId());
            paramsForTxtHeroName.setMargins(150, 0, 0, 0);
            txtHeroName.setTextColor(Color.WHITE);
            txtHeroName.setText(mph.getGameOfMostPlayedHeroes(i).getHeroName());
            txtHeroName.setId(i + 10);

            TextView txtLastMatch = new TextView(this);
            txtLastMatch.setText(mph.getGameOfMostPlayedHeroes(i).getLastMatch());
            RelativeLayout.LayoutParams paramsForTxtLastMatch = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //paramsForTxtLastMatch.addRule(RelativeLayout.RIGHT_OF, img.getId());
            paramsForTxtLastMatch.setMargins(150, 0, 0, 0);
            txtLastMatch.setTextColor(Color.argb(170, 255, 255, 255));
            paramsForTxtLastMatch.addRule(RelativeLayout.BELOW, txtHeroName.getId());

            LinearLayout secondInnerOverviewL = new LinearLayout(this);
            secondInnerOverviewL.setOrientation(LinearLayout.HORIZONTAL);
            RelativeLayout.LayoutParams paramsForSecondInnerOverviewL = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsForSecondInnerOverviewL.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            paramsForSecondInnerOverviewL.addRule(RelativeLayout.CENTER_VERTICAL);

            TextView txtMatchesPlayed = new TextView(this);
            txtMatchesPlayed.setText(mph.getGameOfMostPlayedHeroes(i).getMatchesPlayed());
            txtMatchesPlayed.setTextSize(16);
            txtMatchesPlayed.setTextColor(Color.argb(170, 255, 255, 255));
            txtMatchesPlayed.setPadding(32, 0, 0, 0);
            txtMatchesPlayed.setId(i + 20);

            TextView txtWinRate = new TextView(this);
            txtWinRate.setText(mph.getGameOfMostPlayedHeroes(i).getWinRate());
            txtMatchesPlayed.setTextSize(16);
            txtWinRate.setTextColor(Color.argb(170, 255, 255, 255));
            txtWinRate.setPadding(32, 0, 0, 0);
            txtWinRate.setId(i + 30);

            TextView txtKda = new TextView(this);
            txtKda.setTextColor(Color.rgb(255, 152, 0));
            txtKda.setText(mph.getGameOfMostPlayedHeroes(i).getKda());
            txtMatchesPlayed.setTextSize(16);
            txtKda.setTextColor(Color.rgb(255, 152, 0));
            txtKda.setPadding(32, 0, 0, 0);
            txtKda.setId(i + 40);

            secondInnerOverviewL.addView(txtMatchesPlayed);
            secondInnerOverviewL.addView(txtWinRate);
            secondInnerOverviewL.addView(txtKda);

            overviewL.addView(img, paramsForImg);
            overviewL.addView(txtHeroName, paramsForTxtHeroName);
            overviewL.addView(txtLastMatch, paramsForTxtLastMatch);
            overviewL.addView(secondInnerOverviewL, paramsForSecondInnerOverviewL);
            l.addView(overviewL, paramsForOverviewL);
            layout.addView(l, paramsForL);
        }
    }

    public class Matches {
        Matcher m = Pattern.compile("<tr>" +
                "<td class=\"cell-icon\">" +
                "<div class=\"image-container image-container-hero image-container-icon\">(.*?)" +
                "</tr>").matcher(htmlSrcMatches);
        private String heroName;
        private String imgHeroSrc;
        private String result;
        private String htmlMatchesSrc;
        private String matchTime;
        private String matchRanked;
        private String matchType;
        private String duration;
        private float kill;
        private float death;
        private float assist;
        private String itemsSrc;
        private String[] itemsList;

        public Matches(int index) {
            itemsList = new String[6];
            for (int i = 0; i < index + 1; i++)
                m.find();
            this.htmlMatchesSrc = m.group(1);
            Matcher r = Pattern.compile("</div></td><td class=\"r-none-tablet cell-xxlarge\">(.*?)</div></td>").matcher(this.htmlMatchesSrc);
            r.find();
            if (r.find() == true)
                this.itemsSrc = r.group(1);
            setFields();
            setItemsList(this.htmlMatchesSrc);
        }

        private void setFields() {
            Matcher m = Pattern.compile("<a href=\".*?\"><img class=\"image-hero image-icon\" rel=\"tooltip-remote\" title=\".*?\" data-tooltip-url=\".*?\" src=\"(.*?)\" /></a></div></td><td class=\"cell-large\"><a href=\".*?\">(.*?)</a><div class=\"subtext\">.*?</div></td><td><a class=\".*?\" href=\".*?\">(.*?)</a><div class=\"subtext\"><time datetime=\"(.*?)T(.*?):(.*?):.*?\" title=\".*?\" data-time-ago=\".*?\">.*?</time></div></td><td class=\"r-none-mobile\">(.*?)<div class=\"subtext\">(.*?)</div></td><td>(.{3,8}?)<div class=\"bar bar-default\"><div class=\"segment segment-duration\" style=\".*?;\"></div></div></td><td><span class=\"kda-record\"><span class=\"value\">(.*?)</span>/<span class=\"value\">(.*?)</span>/<span class=\"value\">(.*?)</span></span><div class=\"bar bar-default\"><div class=\"segment segment-kill\" style=\".*?;\"></div><div class=\"segment segment-death\" style=\".*?;\"></div><div class=\"segment segment-assist\" style=\".*?;\"></div></div></td><td class=\"r-none-tablet cell-xxlarge\">.*?").matcher(this.htmlMatchesSrc);
            //Matcher k = Pattern.compile("<a href=\".*?\"><img class=\"image-hero image-icon\" rel=\"tooltip-remote\" title=\".*?\" data-tooltip-url=\".*?\" src=\"(.*?)\" /></a></div></td><td class=\"cell-large\"><a href=\".*?\">(.*?)</a><div class=\"subtext\">.*?</div></td><td><a class=\".*?\" href=\".*?\">(.*?)</a><div class=\"subtext\"><time datetime=\"(.*?)T(.*?):(.*?):.*?\" title=\".*?\" data-time-ago=\".*?\">.*?</time></div></td><td class=\"r-none-mobile\">(.*?)<div class=\"subtext\">(.*?)</div></td><td>(.{3,8}?)<div class=\"bar bar-default\"><div class=\"segment segment-duration\" style=\".*?;\"></div></div></td><td><span class=\"kda-record\"><span class=\"value\">(.*?)</span>/<span class=\"value\">(.*?)</span>/<span class=\"value\">(.*?)</span></span><div class=\"bar bar-default\"><div class=\"segment segment-kill\" style=\".*?;\"></div><div class=\"segment segment-death\" style=\".*?;\"></div><div class=\"segment segment-assist\" style=\".*?;\"></div></div></td><td class=\"r-none-tablet cell-xxlarge\">.*?")
            m.find();
            imgHeroSrc = "http://www.dotabuff.com" + m.group(1);
            heroName = m.group(2).replace("&#39;", "'");
            result = m.group(3);
            matchTime = m.group(4) + " " + m.group(5) + ":" + m.group(6);
            matchRanked = m.group(7);
            matchType = m.group(8);
            duration = m.group(9);
            kill = Float.parseFloat(m.group(10));
            death = Float.parseFloat(m.group(11));
            assist = Float.parseFloat(m.group(12));

        }

        private void setItemsList(String htmlSrcMatches) {
            int index = 0;
            for (int j = 0; j < 6; j++) {
                Matcher m = Pattern.compile("<img class=\"image-item image-icon\" " +
                        "rel=\"tooltip-remote\" " +
                        "title=\".*?\" " +
                        "data-tooltip-url=\".*?\" " +
                        "src=\"(.*?)\" />" +
                        "</a></div>").matcher(this.htmlMatchesSrc);
                for (int i = 0; i < index; i++) {
                    m.find();
                }
                if (m.find() == true)
                    itemsList[j] = "http://www.dotabuff.com" + m.group(1);
                else
                    itemsList[j] = null;
                index++;
            }
        }
    }
}
