package com.autilite.newswithreddit.fetcher;

import android.util.Log;

import com.autilite.newswithreddit.BuildConfig;
import com.autilite.newswithreddit.data.Comment;
import com.autilite.newswithreddit.data.Link;
import com.autilite.newswithreddit.data.Thing;
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
     *
     * more:
     * A list of String ids that are the additional things that can be downloaded but are not because there are too many to list.
     */

    private static final String DEFAULT_SUBREDDITS = "http://www.reddit.com/subreddits/default.json";
    private static final String SUBREDDIT_TEMPLATE = "http://www.reddit.com/r/SUBREDDIT/.json?raw_json=1";
    private static final String COMMENT_TEMPLATE = "http://www.reddit.com/r/SUBREDDIT/comments/COMMENT_ID/.json?raw_json=1";
    // raw_json gives unicode in place of encoding
    private static final String TAG = SubredditLinks.class.getName();

    private String subreddit;
    private String comment_link_id;
    private String url;

    private Link link;

    /**
     * Class for fetching links from a subreddit.
     * @param subreddit
     *      The name of the subreddit or "/" for the front page. Do not include "/r/"
     */
    public SubredditLinks(String subreddit) {
        this.subreddit = subreddit;
        generateUrl();
    }

    public SubredditLinks(String subreddit, String comment_link_id) {
        this.subreddit = subreddit;
        this.comment_link_id = comment_link_id;
        generateUrl();
    }

    private void generateUrl() {
        if (subreddit.equals("/")) {
            url = "http://www.reddit.com/.json";
        } else if (comment_link_id == null) {
            url = SUBREDDIT_TEMPLATE.replace("SUBREDDIT", subreddit);
        } else {
            url = COMMENT_TEMPLATE.replace("SUBREDDIT", subreddit)
                    .replace("COMMENT_ID", comment_link_id);
        }
        Log.v(TAG, "Generated url: " + url);
    }

    public Link getLink() {
        if (link == null) {
            link = fetchLink();
        }
        return link;
    }

    public Link fetchLink() {
        String output;
        try {
            output = NetworkConnection.readContents(url);
        } catch (MalformedURLException e) {
            Log.w(TAG, "Invalid URL: " + e.getMessage());
            return null;
        } catch (IOException e) {
            Log.w(TAG, "Connection error", e);
            return null;
        }
        Thing.ThingFactory tf = Thing.getThingFactory();

        // Make the link
        Link link = null;
        try {
            JSONArray children = new JSONArray(output).getJSONObject(0).getJSONObject("data").getJSONArray("children");
            if (BuildConfig.DEBUG && !(children.length() == 1)) {
                throw new AssertionError("There was more than one link parsed in the comments");
            }
            // the first JSONObject is the post info (t3)
            // the second is the Listing of the comments (t1)
            JSONObject linkObj = children.getJSONObject(0);
            if (!linkObj.getString("kind").equals("t3")) {
                Log.w(TAG, "Link comment is of the wrong kind:\n" + linkObj);
            } else {
                link = (Link) tf.makeThing(linkObj);
            }
        } catch (JSONException e) {
            Log.w(TAG, e);
        }
        return link;
    }

    public List<Comment> fetchTopLevelComments() {
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
        /**
         * Sample comments page json
         * [
            {
                "kind": "Listing",
                "data": {
                    "modhash": "",
                    "children": [
                             {
                                "kind": "t3", // posting
                                "data": {...}
                             }
                            ],
                    "after": null,
                    "before": null
                }
            },
            {
                "kind": "Listing",
                "data": {
                    "modhash": "",
                    "children": [
                                 {
                                    "kind": "t1",
                                    "data": {...} // comment_link_id data
                                 },
                                    .... // more comment_link_id data
                                ],
                    "after": null,
                    "before": null
                }
            }
           ]
         */
        Thing.ThingFactory tf = Thing.getThingFactory();

        // Make the link
        try {
            JSONArray children = new JSONArray(output).getJSONObject(0).getJSONObject("data").getJSONArray("children");
            if (BuildConfig.DEBUG && !(children.length() == 1)) {
                throw new AssertionError("There was more than one link parsed in the comments");
            }
            // the first JSONObject is the post info (t3)
            // the second is the Listing of the comments (t1)
            JSONObject linkObj = children.getJSONObject(0);
            if (!linkObj.getString("kind").equals("t3")) {
                Log.w(TAG, "Link comment is of the wrong kind:\n" + linkObj);
            } else {
                link = (Link) tf.makeThing(linkObj);
            }
        } catch (JSONException e) {
            Log.w(TAG, e);
        }

        // Make the list of comments
        List<Comment> comments = new ArrayList<>();
        JSONObject jsonComment;
        try {
            // the first JSONObject is the post info (t3)
            // the second is the Listing of the comments (t1)
            JSONArray children = new JSONArray(output).getJSONObject(1).getJSONObject("data").getJSONArray("children");
            for (int i = 0; i < children.length(); i++) {
                jsonComment = children.getJSONObject(i);
                if (!jsonComment.getString("kind").equals("t1")) {
                    Log.w(TAG, "Link comment is of the wrong kind:\n" + jsonComment);
                    continue;
                }
                Comment comment = (Comment) tf.makeThing(jsonComment);
                if (comment != null) {
                    comments.add(comment);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return comments;
    }

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
