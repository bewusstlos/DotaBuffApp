package com.example.bewusstlos.dotabuffapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bewus on 4/11/2016.
 */
public class StartActivity extends AppCompatActivity {
    String urlForProfile = null;
    String passUrl = null;
    String htmlSrc = null;
    ArrayList<SearchedHero> searchedHeroes = new ArrayList<SearchedHero>();
    public View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Якого воно не работає???
            startMainActivity(v, searchedHeroes);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final EditText searchProfile = (EditText) findViewById(R.id.search_profile);
        Button btnSearch = (Button) findViewById(R.id.btn_search);
        final LinearLayout layoutProfileLeft = (LinearLayout) findViewById(R.id.layout_profiles_left);
        searchProfile.setHint("UserName...");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileName = searchProfile.getText().toString();
                urlForProfile = "http://www.dotabuff.com/search?utf8=&q=" + searchProfile.getText().toString().replace(" ", "+") + "&commit=Search";
                while (htmlSrc == "" || htmlSrc == null) {
                    setHtmlSrc(urlForProfile);
                }
                for (int i = 0; i < 30; i++) {
                    searchedHeroes.add(i, new SearchedHero(i));
                    RelativeLayout l = new RelativeLayout(StartActivity.this);
                    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rlp.setMargins(8, 0, 8, 8);
                    l.setLayoutParams(rlp);
                    l.setId(i);
                    //l.setLayoutParams(LinearLayout.);
                    ImageView img = new ImageView(StartActivity.this);
                    new DownloadImageTask(img).execute(searchedHeroes.get(i).getImageSrc());
                    img.setId(i + 40);
                    l.addView(img);
                    RelativeLayout innerL = new RelativeLayout(StartActivity.this);
                    RelativeLayout.LayoutParams paramsForInnerL = new RelativeLayout.LayoutParams
                            (RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                    paramsForInnerL.addRule(RelativeLayout.RIGHT_OF, img.getId());
                    TextView txtProfileName = new TextView(StartActivity.this);
                    txtProfileName.setText(searchedHeroes.get(i).getName());
                    txtProfileName.setId(i + 80);
                    TextView txtLastMatch = new TextView(StartActivity.this);
                    txtLastMatch.setText(searchedHeroes.get(i).getLastMatch());
                    txtLastMatch.setId(i + 120);
                    RelativeLayout.LayoutParams paramsForNameLastMatch = new RelativeLayout.LayoutParams
                            (
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                    innerL.addView(txtProfileName, paramsForNameLastMatch);
                    paramsForNameLastMatch.addRule(RelativeLayout.BELOW, txtLastMatch.getId());
                    l.addView(innerL, paramsForNameLastMatch);
                    l.setOnClickListener(onClick);
                    layoutProfileLeft.addView(l, rlp);
                }
                //while(m.find()) {
                //(profileName == m.group(2)) {
                //m.find();
                //passUrl = "http://www.dotabuff.com" + m.group(1);
                //Intent pass = new Intent(StartActivity.this, MainActivity.class);
                //pass.putExtra("EmpID", passUrl);
                //startActivity(pass);
                //}
                //}
            }
        });
    }

    public void startMainActivity(View view, ArrayList<SearchedHero> searchedHeros) {
        Intent pass = new Intent(StartActivity.this, MainActivity.class);
        pass.putExtra("Link", searchedHeros.get(view.getId()).getProfileUrl());
        startActivity(pass);
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
                "<div class=\"body-item\">(.*?)</div></div></div></div></div>").matcher(htmlSrc);
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
            lastMatch = m.group(4);
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
