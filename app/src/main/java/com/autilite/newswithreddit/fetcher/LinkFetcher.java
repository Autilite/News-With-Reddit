package com.autilite.newswithreddit.fetcher;

import android.util.Log;

import com.autilite.newswithreddit.data.Link;
import com.autilite.newswithreddit.data.Thing;
import com.autilite.newswithreddit.util.NetworkConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 01/06/15.
 */
public class LinkFetcher extends RedditFetcher{
    private static final String TAG = LinkFetcher.class.getName();

    public LinkFetcher(String subreddit) {
        super(subreddit);
    }

    @Override
    public void generateUrl() {
        if (urlParams.equals("/") || urlParams.startsWith("/r/")) {
            url = REDDIT_BASE_URL + urlParams + JSON_FORMAT;
        } else {
            url = REDDIT_BASE_URL + REDDIT_SUBREDDIT_BASE + urlParams + JSON_FORMAT;
        }
        Log.i(TAG, "Generated url: " + url);
    }

    public List<Link> fetch() {
        generateUrl();
        String output;
        try {
            output = NetworkConnection.readContents(url);
        } catch (MalformedURLException e) {
            Log.w(TAG, "Invalid URL: " + e.getMessage());
            return new ArrayList<>();
        } catch (IOException e) {
            Log.w(TAG, "Connection error", e);
            return new ArrayList<>();
        }
        List<Link> links = new ArrayList<>();
        try {
            List<Thing> things = Thing.getThingFactory().makeListThing(new JSONObject(output));
            for (Thing thing : things) {
                if (thing instanceof Link)
                    links.add((Link) thing);
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSON parsing or JSONObject construction fail.", e);
        }
        return links;
    }
}
