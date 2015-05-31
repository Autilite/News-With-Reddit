package com.autilite.newswithreddit;

import android.util.Log;

import com.autilite.newswithreddit.data.Link;
import com.autilite.newswithreddit.data.Thing;
import com.autilite.newswithreddit.util.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 5/29/15.
 */
public class SubredditLinks {
    /**
     * Reddit kind:
     * Listing
     * more
     * t1 Comment
     * t2 Account
     * t3 Link
     * t4 Message
     * t5 Subreddit
     * t6 Award
     * t8 PromoCampaign
     *
     * Listing:
     * after / before - only one should be specified. these indicate the fullname of an item in the listing to use as the anchor point of the slice.
     * limit - the maximum number of items to return in this slice of the listing.
     * count - the number of items already seen in this listing. on the html site, the builder uses this to determine when to give values for before and after in the response.
     * show  - optional parameter; if all is passed, filters such as "hide links that I have voted on" will be disabled.
     */

    private static final String DEFAULT_SUBREDDITS = "http://www.reddit.com/subreddits/default.json";
    private static final String SUBREDDIT_TEMPLATE = "http://www.reddit.com/r/SUBREDDIT/.json?raw_json=1";
    // raw_json gives unicode in place of encoding

    private String subreddit;
    private String url;

    /**
     * Class for fetching links from a subreddit.
     * @param subreddit
     *      The name of the subreddit or "/" for the front page. Do not include "/r/"
     */
    public SubredditLinks(String subreddit) {
        this.subreddit = subreddit;
        generateUrl();
    }

    private void generateUrl() {
        if (subreddit.equals("/")) {
            url = "http://www.reddit.com/.json";
        } else {
            url = SUBREDDIT_TEMPLATE.replace("SUBREDDIT", subreddit);
        }
    }

    public List<Link> fetchLinks() {
        String output = NetworkConnection.readContents(url);
        List<Link> links = new ArrayList<>();
        JSONObject entry = new JSONObject();
        try {
            JSONObject data = new JSONObject(output).getJSONObject("data");
            JSONArray children = data.getJSONArray("children");
            for (int i = 0; i < children.length(); i++) {
                entry = children.getJSONObject(i);
                Link link = (Link) Thing.getThingFactory().makeThing(entry);
                links.add(link);
            }
        } catch (JSONException e) {
            Log.e("JSON", "JSON parse exception:\n" + entry, e);
        }
        return links;
    }

    public static List<String> fetchDefaultSubreddits() {
        String output = NetworkConnection.readContents(DEFAULT_SUBREDDITS);
        List<String> subreddits = new ArrayList<>();
        JSONObject entry = new JSONObject();
        try {
            JSONObject data = new JSONObject(output).getJSONObject("data");
            JSONArray children = data.getJSONArray("children");
            for (int i = 0; i < children.length(); i++) {
                entry = children.getJSONObject(i).getJSONObject("data");
                subreddits.add(entry.getString("display_name"));
            }
        } catch (JSONException e) {
            Log.e("JSON", "JSON parse exception:\n" + entry, e);
        }
        return subreddits;
    }
}
