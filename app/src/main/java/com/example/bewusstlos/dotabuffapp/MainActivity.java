package com.example.bewusstlos.dotabuffapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    /*Встановлюється шапка додатку
     *з основною інформацією
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MostPlayedHeroes mph = new MostPlayedHeroes();
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
        Profile profile = new Profile();
        new DownloadImageTask(imgProfileAvatar).execute(profile.getAvatarSrc());
        txtProfileName.setText(profile.getProfileName());
        txtWinRate.setText(profile.getWinRate());
        txtRecordWins.setText(profile.getRecordWins());
        txtRecordLosses.setText(profile.getRecordLosses());
        txtRecordAbandons.setText(profile.getRecordAbandons());
        txtLastMatch.setText(profile.getLastMatch());
    }
    //Клас для встановлення ресурсу картинок по посиланню
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
