package com.autilite.newswithreddit.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kelvin on 5/29/15.
 */
public class NetworkConnection {

    private static final int TIMEOUT = 30000; // 30s
    private static final String TAG = "CON";

    public static HttpURLConnection getConnection (String url) throws IOException {
        HttpURLConnection con = null;
        con = (HttpURLConnection) new URL(url).openConnection();
        con.setReadTimeout(TIMEOUT);
        con.setRequestProperty("User-Agent", "android:com.autilite.newswithreddit:v1 (by /u/null)");
        Log.d(TAG, "url: " + url);
        return con;
    }

    public static String readContents(String url) throws IOException {
        HttpURLConnection con = getConnection(url);
        if (con == null)
            return null;
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }
}
