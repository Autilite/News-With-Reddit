package com.autilite.newswithreddit;

import android.util.Log;

import com.autilite.newswithreddit.util.NetworkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 5/29/15.
 */
public class SubredditPosts {
    private final String URL_TEMPLATE = "http://www.reddit.com/r/SUBREDDIT/.json";

    private String subreddit;
    private String url;

    /**
     * Class for fetching posts from a subreddit
     * @param subreddit
     *      The name of the subreddit or "/" for the front page. Do not include "/r/"
     */
    public SubredditPosts(String subreddit) {
        this.subreddit = subreddit;
        generateUrl();
    }

    private void generateUrl() {
        if (subreddit.equals("/")) {
            url = "http://www.reddit.com/.json";
        } else {
            url = URL_TEMPLATE.replace("SUBREDDIT", subreddit);
        }
    }

    public List<Post> fetchPosts() {
        String output = NetworkConnection.readContents(url);
        List<Post> posts = new ArrayList<>();
        JSONObject entry = new JSONObject();
        try {
            JSONObject data = new JSONObject(output).getJSONObject("data");
            JSONArray children = data.getJSONArray("children");
            for (int i = 0; i < children.length(); i++) {
                entry = children.getJSONObject(i).getJSONObject("data");
                Post post = new Post.PostBuilder().setDomain(entry.getString("domain"))
                        .setSubreddit(entry.getString("subreddit"))
                        .setThumbnail(entry.getString("thumbnail"))
                        .setPermalink(entry.getString("permalink"))
                        .setUrl(entry.getString("url"))
                        .setId(entry.getString("id"))
                        .setName(entry.getString("name"))
                        .setTitle(entry.getString("title"))
                        .setAuthor(entry.getString("author"))
                        .setScore(entry.getInt("score"))
                        .setNum_comments(entry.getInt("num_comments"))
                        .setVisited(entry.getBoolean("visited"))
                        .setOver_18(entry.getBoolean("over_18"))
                        .setHidden(entry.getBoolean("hidden"))
                        .build();
                posts.add(post);
            }
        } catch (JSONException e) {
            Log.e("JSON", "Post JSON parse exception:\n" + entry, e);
        }
        return posts;
    }
}
