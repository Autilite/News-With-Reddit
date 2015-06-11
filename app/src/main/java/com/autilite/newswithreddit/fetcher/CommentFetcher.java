package com.autilite.newswithreddit.fetcher;

import android.net.Uri;
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
 * Created by kelvin on 6/11/15.
 */
public class CommentFetcher extends RedditFetcher<Comment> {
    private static final String TAG = CommentFetcher.class.getName();

    public static enum Order {
        TOP, NEW, CONTROVERSIAL, OLD, QA
    }

    private String subreddit;
    private String commentLinkId;
    private String before, after;
    private int limit;
    private Order order;

    private Link link;

    public CommentFetcher(String subreddit, String commentLinkId) {
        this.subreddit = subreddit;
        this.commentLinkId = commentLinkId;
    }

    @Override
    public void generateUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("www.reddit.com")
                .encodedPath("/r/")
                .appendPath(subreddit)
                .appendEncodedPath("comments")
                .appendEncodedPath(commentLinkId);


        if (after != null && !after.equals("")) {
            builder.appendQueryParameter("after", after);
        }

        builder.appendEncodedPath(".json")
               .appendQueryParameter("raw_json", "1");

        url = builder.build().toString();

        Log.i(TAG, "Generated new uri: " + url);
    }

    @Override
    public List<Comment> fetch() {
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
        try {
            JSONArray children = new JSONArray(output).getJSONObject(0).getJSONObject("data").getJSONArray("children");
            link = fetchLink(children);
            children = new JSONArray(output).getJSONObject(1).getJSONObject("data").getJSONArray("children");
            return fetchCommentList(children);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();


    }

    private Link fetchLink(JSONArray children) {
        Thing.ThingFactory tf = Thing.getThingFactory();
        Link link = null;
        try {
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

    private List<Comment> fetchCommentList(JSONArray children) {
        Thing.ThingFactory tf = Thing.getThingFactory();
        // Make the list of comments
        List<Comment> comments = new ArrayList<>();
        JSONObject jsonComment;
        try {
            // the first JSONObject is the post info (t3)
            // the second is the Listing of the comments (t1)
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

    public Link getLink() {
        return link;
    }
}
