package com.example.bewusstlos.dotabuffapp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bewus on 4/11/2016.
 */
public class StartActivity extends AppCompatActivity {
    String urlForProfile = null;
    String htmlSrc = null;
    LinearLayout layoutProfileLeft;
    EditText searchProfile;
    ArrayList<SearchedHero> searchedHeroes = new ArrayList<SearchedHero>();
    View.OnClickListener onItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent pass = new Intent(StartActivity.this, MainActivity.class);
                String extra = searchedHeroes.get(v.getId()).getProfileUrl();
                pass.putExtra("Link", extra);
                startActivity(pass, new Bundle());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    };
    View.OnClickListener onSearchClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            urlForProfile = "http://www.dotabuff.com/search?utf8=&q=" + searchProfile.getText().toString().replace(" ", "+") + "&commit=Search";
            while (htmlSrc == "" || htmlSrc == null) {
                setHtmlSrc(urlForProfile);
            }
            for (int i = 0; i < 30; i++) {
                try {
                    searchedHeroes.add(i, new SearchedHero(i));
                    LinearLayout l = new LinearLayout(StartActivity.this);
                    LinearLayout.LayoutParams paramsForL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    paramsForL.setMargins(16, 16, 16, 0);
                    l.setOrientation(LinearLayout.HORIZONTAL);
                    l.setBackgroundColor(Color.rgb(55, 71, 79));
                    l.setId(i + 40);

                    ImageView img = new ImageView(StartActivity.this);
                    new DownloadImageTask(img).execute(searchedHeroes.get(i).getImageSrc());
                    img.setClickable(true);
                    img.setOnClickListener(onItemClick);
                    LinearLayout.LayoutParams paramsForImg = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    paramsForImg.setMargins(8, 8, 8, 8);
                    img.setId(i);
                    l.addView(img, paramsForImg);

                    LinearLayout innerL = new LinearLayout(StartActivity.this);
                    LinearLayout.LayoutParams paramsForInnerL = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    paramsForInnerL.setMargins(8, 0, 0, 8);
                    innerL.setOrientation(LinearLayout.VERTICAL);

                    TextView txtProfileName = new TextView(StartActivity.this);
                    txtProfileName.setText(searchedHeroes.get(i).getName());
                    txtProfileName.setTextSize(24);
                    txtProfileName.setTextColor(Color.WHITE);
                    txtProfileName.setId(i + 80);

                    TextView txtLastMatch = new TextView(StartActivity.this);
                    txtLastMatch.setText(searchedHeroes.get(i).getLastMatch());
                    txtLastMatch.setId(i + 120);

                    TextView txtLastMatchLabel = new TextView(StartActivity.this);
                    txtLastMatchLabel.setText("Last Match");
                    txtLastMatch.setTextColor(Color.rgb(255, 152, 0));
                    txtLastMatchLabel.setId(i + 160);

                    innerL.addView(txtProfileName);
                    innerL.addView(txtLastMatchLabel);
                    innerL.addView(txtLastMatch);

                    l.addView(innerL, paramsForInnerL);

                    layoutProfileLeft.addView(l, paramsForL);
                }
                catch (Exception e){
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        searchProfile = (EditText) findViewById(R.id.search_profile);
        Button btnSearch = (Button) findViewById(R.id.btn_search);
        layoutProfileLeft = (LinearLayout) findViewById(R.id.layout_profiles_left);
        searchProfile.setHint("UserName...");
        btnSearch.setOnClickListener(onSearchClick);
    }

    public void setHtmlSrc(String urlForProfile) {
        RequestTask requestTask = new RequestTask();
        requestTask.execute(urlForProfile);
        try {
            htmlSrc = requestTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("Interrupt!", "");
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.i("Execution!", "");
        }
    }

    class SearchedHero {
        Matcher m = Pattern.compile("<div class=\"result result-player\" data-filter-name=\"players\" " +
                "data-filter-value=\".*?\"><div class=\"inner\" data-player-id=\".*?\" " +
                "data-search-link=\".*?\" data-search-rank=\".*?\">" +
                "<div class=\"avatar\"><div class=\"image-container image-container-player image-container-avatar\">" +
                "<a href=\"(.*?)\"><img alt=\".*?\" title=\".*?\" class=\"image-player image-avatar\" " +
                "rel=\"tooltip-remote\" data-tooltip-url=\"/.*?\" " +
                "src=\"(.*?)\" />" +
                "</a></div></div><div class=\"identity\"><div class=\"head\"><a class=\"link-type-player\" " +
                "href=\".*?\">(.*?)</a> </div><div class=\"body\">" +
                "<div class=\"body-item\">.*?<time datetime=\"(.*?)T(.*?):(.*?):.*?\" title=\".*?\" data-time-ago=\".*?\">.*?</time></div></div></div></div></div>").matcher(htmlSrc);
        private String name;
        private String imageSrc;
        private String profileUrl;
        private String lastMatch;

        public SearchedHero(int index) {
            for (int i = 0; i < index + 1; i++)
                m.find();
            profileUrl = "http://dotabuff.com" + m.group(1);
            imageSrc = m.group(2);
            name = m.group(3);
            lastMatch = m.group(4) + " " + (Integer.parseInt(m.group(5)) + 3) + ":" + m.group(6);
        }

        public String getName() {
            return name;
        }

        public String getImageSrc() {
            return imageSrc;
        }

        public String getProfileUrl() {
            return profileUrl;
        }

        public String getLastMatch() {
            return lastMatch;
        }
    }
}