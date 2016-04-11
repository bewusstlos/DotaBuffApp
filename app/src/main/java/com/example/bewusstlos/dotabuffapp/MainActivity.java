package com.example.bewusstlos.dotabuffapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    String url;
    @Override
    /*Встановлюється шапка додатку
     *з основною інформацією
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        url = b.getString("EmpID");
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

        ImageView imgProfileAvatar = (ImageView)findViewById(R.id.img_profile_avatar);
        TextView txtProfileName = (TextView)findViewById(R.id.txt_profile_name);
        TextView txtWinRate = (TextView)findViewById(R.id.txt_win_rate);
        TextView txtRecordWins = (TextView)findViewById(R.id.txt_record_wins);
        TextView txtRecordLosses = (TextView)findViewById(R.id.txt_record_losses);
        TextView txtRecordAbandons = (TextView)findViewById(R.id.txt_record_abandons);
        TextView txtLastMatch = (TextView)findViewById(R.id.txt_last_match);
        Profile profile = new Profile(url);
        new DownloadImageTask(imgProfileAvatar).execute(profile.getAvatarSrc());
        txtProfileName.setText(profile.getProfileName());
        txtWinRate.setText(profile.getWinRate());
        txtRecordWins.setText(profile.getRecordWins());
        txtRecordLosses.setText(profile.getRecordLosses());
        txtRecordAbandons.setText(profile.getRecordAbandons());
        txtLastMatch.setText(profile.getLastMatch());

        setOverviewLayout();
    }

    public void setOverviewLayout() {
        MostPlayedHeroes mph = new MostPlayedHeroes(url);

        ImageView imgHeroOverview0 = (ImageView) findViewById(R.id.overview_hero_avatar_1);
        TextView txtHeroNameOverview0 = (TextView) findViewById(R.id.overview_hero_name_1);
        TextView txtHeroLastMatchOverview0 = (TextView) findViewById(R.id.overview_hero_last_match_1);
        TextView txtHeroGamesPlayedOverview0 = (TextView) findViewById(R.id.overview_hero_matches_played_1);
        TextView txtHeroWinRateOverview0 = (TextView) findViewById(R.id.overview_hero_win_rate_1);
        TextView txtHeroKdaRatioOverview0 = (TextView) findViewById(R.id.overview_hero_kda_ratio_1);

        new DownloadImageTask(imgHeroOverview0).execute(mph.getGameOfMostPlayedHeroes(0).getHeroAvatarSrc());
        txtHeroNameOverview0.setText(mph.getGameOfMostPlayedHeroes(0).getHeroName());
        txtHeroLastMatchOverview0.setText(mph.getGameOfMostPlayedHeroes(0).getLastMatch());
        txtHeroGamesPlayedOverview0.setText(mph.getGameOfMostPlayedHeroes(0).getMatchesPlayed());
        txtHeroWinRateOverview0.setText(mph.getGameOfMostPlayedHeroes(0).getWinRate());
        txtHeroKdaRatioOverview0.setText(mph.getGameOfMostPlayedHeroes(0).getKda());

        ImageView imgHeroOverview1 = (ImageView) findViewById(R.id.overview_hero_avatar_2);
        TextView txtHeroNameOverview1 = (TextView) findViewById(R.id.overview_hero_name_2);
        TextView txtHeroLastMatchOverview1 = (TextView) findViewById(R.id.overview_hero_last_match_2);
        TextView txtHeroGamesPlayedOverview1 = (TextView) findViewById(R.id.overview_hero_matches_played_2);
        TextView txtHeroWinRateOverview1 = (TextView) findViewById(R.id.overview_hero_win_rate_2);
        TextView txtHeroKdaRatioOverview1 = (TextView) findViewById(R.id.overview_hero_kda_ratio_2);

        new DownloadImageTask(imgHeroOverview1).execute(mph.getGameOfMostPlayedHeroes(1).getHeroAvatarSrc());
        txtHeroNameOverview1.setText(mph.getGameOfMostPlayedHeroes(1).getHeroName());
        txtHeroLastMatchOverview1.setText(mph.getGameOfMostPlayedHeroes(1).getLastMatch());
        txtHeroGamesPlayedOverview1.setText(mph.getGameOfMostPlayedHeroes(1).getMatchesPlayed());
        txtHeroWinRateOverview1.setText(mph.getGameOfMostPlayedHeroes(1).getWinRate());
        txtHeroKdaRatioOverview1.setText(mph.getGameOfMostPlayedHeroes(1).getKda());

        ImageView imgHeroOverview2 = (ImageView) findViewById(R.id.overview_hero_avatar_3);
        TextView txtHeroNameOverview2 = (TextView) findViewById(R.id.overview_hero_name_3);
        TextView txtHeroLastMatchOverview2 = (TextView) findViewById(R.id.overview_hero_last_match_3);
        TextView txtHeroGamesPlayedOverview2 = (TextView) findViewById(R.id.overview_hero_matches_played_3);
        TextView txtHeroWinRateOverview2 = (TextView) findViewById(R.id.overview_hero_win_rate_3);
        TextView txtHeroKdaRatioOverview2 = (TextView) findViewById(R.id.overview_hero_kda_ratio_3);

        new DownloadImageTask(imgHeroOverview2).execute(mph.getGameOfMostPlayedHeroes(2).getHeroAvatarSrc());
        txtHeroNameOverview2.setText(mph.getGameOfMostPlayedHeroes(2).getHeroName());
        txtHeroLastMatchOverview2.setText(mph.getGameOfMostPlayedHeroes(2).getLastMatch());
        txtHeroGamesPlayedOverview2.setText(mph.getGameOfMostPlayedHeroes(2).getMatchesPlayed());
        txtHeroWinRateOverview2.setText(mph.getGameOfMostPlayedHeroes(2).getWinRate());
        txtHeroKdaRatioOverview2.setText(mph.getGameOfMostPlayedHeroes(2).getKda());

        ImageView imgHeroOverview3 = (ImageView) findViewById(R.id.overview_hero_avatar_4);
        TextView txtHeroNameOverview3 = (TextView) findViewById(R.id.overview_hero_name_4);
        TextView txtHeroLastMatchOverview3 = (TextView) findViewById(R.id.overview_hero_last_match_4);
        TextView txtHeroGamesPlayedOverview3 = (TextView) findViewById(R.id.overview_hero_matches_played_4);
        TextView txtHeroWinRateOverview3 = (TextView) findViewById(R.id.overview_hero_win_rate_4);
        TextView txtHeroKdaRatioOverview3 = (TextView) findViewById(R.id.overview_hero_kda_ratio_4);

        new DownloadImageTask(imgHeroOverview3).execute(mph.getGameOfMostPlayedHeroes(3).getHeroAvatarSrc());
        txtHeroNameOverview3.setText(mph.getGameOfMostPlayedHeroes(3).getHeroName());
        txtHeroLastMatchOverview3.setText(mph.getGameOfMostPlayedHeroes(3).getLastMatch());
        txtHeroGamesPlayedOverview3.setText(mph.getGameOfMostPlayedHeroes(3).getMatchesPlayed());
        txtHeroWinRateOverview3.setText(mph.getGameOfMostPlayedHeroes(3).getWinRate());
        txtHeroKdaRatioOverview3.setText(mph.getGameOfMostPlayedHeroes(3).getKda());

        ImageView imgHeroOverview4 = (ImageView) findViewById(R.id.overview_hero_avatar_5);
        TextView txtHeroNameOverview4 = (TextView) findViewById(R.id.overview_hero_name_5);
        TextView txtHeroLastMatchOverview4 = (TextView) findViewById(R.id.overview_hero_last_match_5);
        TextView txtHeroGamesPlayedOverview4 = (TextView) findViewById(R.id.overview_hero_matches_played_5);
        TextView txtHeroWinRateOverview4 = (TextView) findViewById(R.id.overview_hero_win_rate_5);
        TextView txtHeroKdaRatioOverview4 = (TextView) findViewById(R.id.overview_hero_kda_ratio_5);

        new DownloadImageTask(imgHeroOverview4).execute(mph.getGameOfMostPlayedHeroes(4).getHeroAvatarSrc());
        txtHeroNameOverview4.setText(mph.getGameOfMostPlayedHeroes(4).getHeroName());
        txtHeroLastMatchOverview4.setText(mph.getGameOfMostPlayedHeroes(4).getLastMatch());
        txtHeroGamesPlayedOverview4.setText(mph.getGameOfMostPlayedHeroes(4).getMatchesPlayed());
        txtHeroWinRateOverview4.setText(mph.getGameOfMostPlayedHeroes(4).getWinRate());
        txtHeroKdaRatioOverview4.setText(mph.getGameOfMostPlayedHeroes(4).getKda());

        ImageView imgHeroOverview5 = (ImageView) findViewById(R.id.overview_hero_avatar_6);
        TextView txtHeroNameOverview5 = (TextView) findViewById(R.id.overview_hero_name_6);
        TextView txtHeroLastMatchOverview5 = (TextView) findViewById(R.id.overview_hero_last_match_6);
        TextView txtHeroGamesPlayedOverview5 = (TextView) findViewById(R.id.overview_hero_matches_played_6);
        TextView txtHeroWinRateOverview5 = (TextView) findViewById(R.id.overview_hero_win_rate_6);
        TextView txtHeroKdaRatioOverview5 = (TextView) findViewById(R.id.overview_hero_kda_ratio_6);

        new DownloadImageTask(imgHeroOverview5).execute(mph.getGameOfMostPlayedHeroes(4).getHeroAvatarSrc());
        txtHeroNameOverview5.setText(mph.getGameOfMostPlayedHeroes(5).getHeroName());
        txtHeroLastMatchOverview5.setText(mph.getGameOfMostPlayedHeroes(5).getLastMatch());
        txtHeroGamesPlayedOverview5.setText(mph.getGameOfMostPlayedHeroes(5).getMatchesPlayed());
        txtHeroWinRateOverview5.setText(mph.getGameOfMostPlayedHeroes(5).getWinRate());
        txtHeroKdaRatioOverview5.setText(mph.getGameOfMostPlayedHeroes(5).getKda());

        ImageView imgHeroOverview6 = (ImageView) findViewById(R.id.overview_hero_avatar_7);
        TextView txtHeroNameOverview6 = (TextView) findViewById(R.id.overview_hero_name_7);
        TextView txtHeroLastMatchOverview6 = (TextView) findViewById(R.id.overview_hero_last_match_7);
        TextView txtHeroGamesPlayedOverview6 = (TextView) findViewById(R.id.overview_hero_matches_played_7);
        TextView txtHeroWinRateOverview6 = (TextView) findViewById(R.id.overview_hero_win_rate_7);
        TextView txtHeroKdaRatioOverview6 = (TextView) findViewById(R.id.overview_hero_kda_ratio_7);

        new DownloadImageTask(imgHeroOverview6).execute(mph.getGameOfMostPlayedHeroes(6).getHeroAvatarSrc());
        txtHeroNameOverview6.setText(mph.getGameOfMostPlayedHeroes(6).getHeroName());
        txtHeroLastMatchOverview6.setText(mph.getGameOfMostPlayedHeroes(6).getLastMatch());
        txtHeroGamesPlayedOverview6.setText(mph.getGameOfMostPlayedHeroes(6).getMatchesPlayed());
        txtHeroWinRateOverview6.setText(mph.getGameOfMostPlayedHeroes(6).getWinRate());
        txtHeroKdaRatioOverview6.setText(mph.getGameOfMostPlayedHeroes(6).getKda());

        ImageView imgHeroOverview7 = (ImageView) findViewById(R.id.overview_hero_avatar_8);
        TextView txtHeroNameOverview7 = (TextView) findViewById(R.id.overview_hero_name_8);
        TextView txtHeroLastMatchOverview7 = (TextView) findViewById(R.id.overview_hero_last_match_8);
        TextView txtHeroGamesPlayedOverview7 = (TextView) findViewById(R.id.overview_hero_matches_played_8);
        TextView txtHeroWinRateOverview7 = (TextView) findViewById(R.id.overview_hero_win_rate_8);
        TextView txtHeroKdaRatioOverview7 = (TextView) findViewById(R.id.overview_hero_kda_ratio_8);

        new DownloadImageTask(imgHeroOverview7).execute(mph.getGameOfMostPlayedHeroes(4).getHeroAvatarSrc());
        txtHeroNameOverview7.setText(mph.getGameOfMostPlayedHeroes(7).getHeroName());
        txtHeroLastMatchOverview7.setText(mph.getGameOfMostPlayedHeroes(7).getLastMatch());
        txtHeroGamesPlayedOverview7.setText(mph.getGameOfMostPlayedHeroes(7).getMatchesPlayed());
        txtHeroWinRateOverview7.setText(mph.getGameOfMostPlayedHeroes(7).getWinRate());
        txtHeroKdaRatioOverview7.setText(mph.getGameOfMostPlayedHeroes(7).getKda());

        ImageView imgHeroOverview8 = (ImageView) findViewById(R.id.overview_hero_avatar_9);
        TextView txtHeroNameOverview8 = (TextView) findViewById(R.id.overview_hero_name_9);
        TextView txtHeroLastMatchOverview8 = (TextView) findViewById(R.id.overview_hero_last_match_9);
        TextView txtHeroGamesPlayedOverview8 = (TextView) findViewById(R.id.overview_hero_matches_played_9);
        TextView txtHeroWinRateOverview8 = (TextView) findViewById(R.id.overview_hero_win_rate_9);
        TextView txtHeroKdaRatioOverview8 = (TextView) findViewById(R.id.overview_hero_kda_ratio_9);

        new DownloadImageTask(imgHeroOverview8).execute(mph.getGameOfMostPlayedHeroes(8).getHeroAvatarSrc());
        txtHeroNameOverview8.setText(mph.getGameOfMostPlayedHeroes(8).getHeroName());
        txtHeroLastMatchOverview8.setText(mph.getGameOfMostPlayedHeroes(8).getLastMatch());
        txtHeroGamesPlayedOverview8.setText(mph.getGameOfMostPlayedHeroes(8).getMatchesPlayed());
        txtHeroWinRateOverview8.setText(mph.getGameOfMostPlayedHeroes(8).getWinRate());
        txtHeroKdaRatioOverview8.setText(mph.getGameOfMostPlayedHeroes(8).getKda());

        ImageView imgHeroOverview9 = (ImageView) findViewById(R.id.overview_hero_avatar_10);
        TextView txtHeroNameOverview9 = (TextView) findViewById(R.id.overview_hero_name_10);
        TextView txtHeroLastMatchOverview9 = (TextView) findViewById(R.id.overview_hero_last_match_10);
        TextView txtHeroGamesPlayedOverview9 = (TextView) findViewById(R.id.overview_hero_matches_played_10);
        TextView txtHeroWinRateOverview9 = (TextView) findViewById(R.id.overview_hero_win_rate_10);
        TextView txtHeroKdaRatioOverview9 = (TextView) findViewById(R.id.overview_hero_kda_ratio_10);

        new DownloadImageTask(imgHeroOverview9).execute(mph.getGameOfMostPlayedHeroes(9).getHeroAvatarSrc());
        txtHeroNameOverview9.setText(mph.getGameOfMostPlayedHeroes(9).getHeroName());
        txtHeroLastMatchOverview9.setText(mph.getGameOfMostPlayedHeroes(9).getLastMatch());
        txtHeroGamesPlayedOverview9.setText(mph.getGameOfMostPlayedHeroes(9).getMatchesPlayed());
        txtHeroWinRateOverview9.setText(mph.getGameOfMostPlayedHeroes(9).getWinRate());
        txtHeroKdaRatioOverview9.setText(mph.getGameOfMostPlayedHeroes(9).getKda());
    }
}