package com.example.bewusstlos.dotabuffapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by bewus on 4/10/2016.
 */
public class RequestTask extends AsyncTask<String, String, String> {
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
        } catch (Exception e) {
        }
        return sb.toString();
    }
}
