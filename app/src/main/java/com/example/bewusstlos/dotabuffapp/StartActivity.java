package com.example.bewusstlos.dotabuffapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final EditText searchProfile = (EditText) findViewById(R.id.search_profile);
        Button btnSearch = (Button) findViewById(R.id.btn_search);
        searchProfile.setHint("UserName...");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileName = searchProfile.getText().toString();
                urlForProfile = "http://www.dotabuff.com/search?utf8=&q=" + searchProfile.getText().toString().replace(" ", "+") + "&button=";
                setHtmlSrc(urlForProfile);
                Matcher m = Pattern.compile("<a class=\"link-type-player\" href=\"(.*?)\">(.*?)</a>").matcher(htmlSrc);
                m.find();
                passUrl = "http://www.dotabuff.com" + m.group(1);
                Intent pass = new Intent(StartActivity.this, MainActivity.class);
                pass.putExtra("EmpID", passUrl);
                startActivity(pass);
            }
        });
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
}
