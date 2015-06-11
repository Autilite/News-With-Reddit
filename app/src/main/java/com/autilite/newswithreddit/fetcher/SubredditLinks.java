package com.autilite.newswithreddit.fetcher;

import android.util.Log;

import com.autilite.newswithreddit.util.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 5/29/15.
 */
public class SubredditLinks {
    private static final String DEFAULT_SUBREDDITS = "http://www.reddit.com/subreddits/default.json";
    private static final String TAG = SubredditLinks.class.getName();

    public static List<String> fetchDefaultSubreddits() {
        String output;
        try {
            output = NetworkConnection.readContents(DEFAULT_SUBREDDITS);
        } catch (MalformedURLException e) {
            Log.w(TAG, "Invalid URL: " + e.getMessage());
            return new ArrayList<>();
        } catch (IOException e) {
            Log.w(TAG, "Connection error", e);
            return new ArrayList<>();
        }
        List<String> subreddits = new ArrayList<>();
        JSONObject jsonSubreddit = new JSONObject();
        try {
            JSONObject data = new JSONObject(output).getJSONObject("data");
            JSONArray children = data.getJSONArray("children");
            for (int i = 0; i < children.length(); i++) {
                jsonSubreddit = children.getJSONObject(i).getJSONObject("data");
                subreddits.add(jsonSubreddit.getString("display_name"));
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSON parse exception:\n" + jsonSubreddit, e);
        }
        return subreddits;
    }
}
