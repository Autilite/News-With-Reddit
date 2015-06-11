package com.autilite.newswithreddit.fetcher;

import android.net.Uri;
import android.util.Log;

import com.autilite.newswithreddit.data.Link;
import com.autilite.newswithreddit.data.Thing;
import com.autilite.newswithreddit.util.NetworkConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 01/06/15.
 */
public class LinkFetcher extends RedditFetcher{
    private static final String TAG = LinkFetcher.class.getName();

    public static enum Order {
        HOT, NEW, RISING, CONTROVERSIAL, TOP
    }

    private boolean isFrontpage;
    private String subreddit;
    private String before, after;
    private int limit;
    private Order order;

    public LinkFetcher(String subreddit) {
        this.subreddit = subreddit;
        isFrontpage = subreddit.equals("/");
    }

    @Override
    public void generateUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("www.reddit.com");

        if (isFrontpage) {
            builder.encodedPath("/");
        } else {
            builder.encodedPath("/r/");
            builder.appendPath(subreddit);
        }

        if (after != null && !after.equals("")) {
            builder.appendQueryParameter("after", after);
        }

        builder.appendEncodedPath(".json");

        url = builder.build().toString();

        Log.i(TAG, "Generated new uri: " + url);

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
            JSONObject listing = new JSONObject(output);
            List<Thing> things = Thing.getThingFactory().makeListThing(listing);
            for (Thing thing : things) {
                if (thing instanceof Link)
                    links.add((Link) thing);
            }
            after = listing.getJSONObject("data").getString("after");
        } catch (JSONException e) {
            Log.e(TAG, "JSON parsing or JSONObject construction fail.", e);
        }
        return links;
    }
}
