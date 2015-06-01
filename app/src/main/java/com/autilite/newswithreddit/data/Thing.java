package com.autilite.newswithreddit.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 5/30/15.
 */
public abstract class Thing {
    private static ThingFactory instance = null;
    protected String id;
    protected String name;
    protected String kind;

    protected abstract void setKind();
    public static ThingFactory getThingFactory() {
        if (instance == null) {
            return new ThingFactory();
        } else return instance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public static class ThingFactory {

        public Thing makeThing(JSONObject object) throws JSONException {
            String kind = object.getString("kind");
            switch (kind){
                case "t1":
                    return makeComment(object);
                case "t3":
                    return makeLink(object);
                case "t4":
                    // TODO
                    break;
                case "t5":
                    break;
                case "more":
                    Log.d("Kind", "More kind " + object.toString());
                    break;
                default:
                    return null;
            }
            return null;
        }

        public List<Thing> makeListThing(JSONObject listing) throws JSONException {
            if (listing == null || listing.length() == 0) {
                return new ArrayList<>();
            }
            if (!listing.getString("kind").equals("Listing")) {
                throw new JSONException("Incorrect thing kind");
            }
            ArrayList<Thing> things = new ArrayList<>();
            JSONArray children = listing.getJSONObject("data").getJSONArray("children");
            for (int i = 0; i < children.length(); i++) {
                JSONObject thingObject = children.getJSONObject(i);
                Thing thing = makeThing(thingObject);
                if (thing != null) {
                    things.add(thing);
                } else {
                    break;
                }
            }
            return things;
        }

        private List<Comment> makeCommentReplyListing(JSONObject listing, int level) throws JSONException {
            if (listing == null || listing.length() == 0) {
                return new ArrayList<>();
            }
            if (!listing.getString("kind").equals("Listing")) {
                throw new JSONException("Incorrect thing kind");
            }
            ArrayList<Comment> comments = new ArrayList<>();
            JSONArray children = listing.getJSONObject("data").getJSONArray("children");
            for (int i = 0; i < children.length(); i++) {
                JSONObject thingObject = children.getJSONObject(i);
                Comment comment = makeComment(thingObject, level);
                if (comment != null) {
                    comments.add(comment);
                } else {
                    break;
                }
            }
            return comments;
        }

        private Thing makeComment(JSONObject object) throws JSONException {
            return makeComment(object, 0);
        }

        private Comment makeComment(JSONObject object, int level) throws JSONException {
            // check if the kind is actually a comment
            String kind = object.getString("kind");
            if (!kind.equals("t1")) {
                return null;
            }
            JSONObject entry = object.getJSONObject("data");
            Comment.CommentBuilder builder = new Comment.CommentBuilder();
            try {
                builder.setId(entry.getString("id"))
                        // TODO level
                        .setLevel(level)
                        .setName(entry.getString("name"))
                        .setApproved_by(entry.getString("approved_by"))
                        .setAuthor(entry.getString("author"))
                        .setAuthor_flair_css_class(entry.getString("author_flair_css_class"))
                        .setAuthor_flare_text(entry.getString("author_flair_text"))
                        .setBanned_by(entry.getString("banned_by"))
                        .setBody(entry.getString("body"))
                        .setBody_html(entry.getString("body_html"))
                                // TODO .setEdited(entry.getLong("edited")) -- can be boolean or long
                        .setGilded(entry.getInt("gilded"))
                                // TODO .setLink_author(entry.getString("link_author"))
                        .setLink_id(entry.getString("link_id"))
                                // TODO .setLink_title(entry.getString("link_title"))
                                // TODO .setLink_url(entry.getString("link_url"))
                        .setParent_id(entry.getString("parent_id"))
                        .setSaved(entry.getBoolean("saved"))
                        .setScore(entry.getInt("score"))
                        .setScore_hidden(entry.getBoolean("score_hidden"))
                        .setSubreddit(entry.getString("subreddit"))
                        .setSubreddit_id(entry.getString("subreddit_id"))
                        .setDistinguished(entry.getString("distinguished"))
                        .setCreated(entry.getLong("created"))
                        .setCreated_utc(entry.getLong("created_utc"));
                if (!String.valueOf(entry.get("likes")).equals("null")) {
                    builder.setLikes(entry.getBoolean("likes"));
                }
                if (!String.valueOf(entry.get("num_reports")).equals("null")) {
                    builder.setNum_reports(entry.getInt("num_reports"));
                }
                String replyString = entry.getString("replies");
                List<Comment> replies;
                if (replyString.trim().equals("")) {
                    replies = new ArrayList<>();
                } else {
                    JSONObject replyObject = new JSONObject(replyString);
                    replies = makeCommentReplyListing(replyObject, level+1);
                }
                builder.setReplies(replies);
                // TODO kind: "more" replies
                return builder.createComment();
            } catch (JSONException e) {
                Log.d("JSON", e.getMessage() + "\n" + object.toString(), e);
                throw e;
            }
        }

        private Thing makeLink(JSONObject object) throws JSONException {
            JSONObject entry = object.getJSONObject("data");
            Link.LinkBuilder builder = new Link.LinkBuilder()
                    .setId(entry.getString("id"))
                    .setName(entry.getString("name"))
                    .setAuthor(entry.getString("author"))
                    .setAuthor_flair_css_class(entry.getString("author_flair_css_class"))
                    .setAuthor_flare_text(entry.getString("author_flair_text"))
                    .setClicked(entry.getString("clicked"))
                    .setDomain(entry.getString("domain"))
                    .setHidden(entry.getBoolean("hidden"))
                    .setIs_self(entry.getBoolean("is_self"))
//                    .setLikes(entry.getBoolean("likes"))  // true up; false down; null none
                    .setLink_flair_css_class(entry.getString("link_flair_css_class"))
                    .setLink_flair_text(entry.getString("link_flair_text"))
                    // TODO Object media; // streaming video. info about video and origin
                    //Object media_embed; // technical embed specific info
                    .setNum_comments(entry.getInt("num_comments"))
                    .setOver_18(entry.getBoolean("over_18"))
                    .setPermalink(entry.getString("permalink"))
                    .setSaved(entry.getBoolean("saved"))
                    .setScore(entry.getInt("score"))
                    .setSelftext(entry.getString("selftext"))
                    .setSelftext_html(entry.getString("selftext_html"))
                    .setSubreddit(entry.getString("subreddit"))
                    .setSubreddit_id(entry.getString("subreddit_id"))
                    .setThumbnail(entry.getString("thumbnail"))
                    .setTitle(entry.getString("title"))
                    .setUrl(entry.getString("url"))
                    // TODO .setEdited(entry.getLong("edited")) -- can be boolean or long
                    .setDistinguished(entry.getString("distinguished"))
                    .setStickied(entry.getBoolean("stickied"))
                    .setCreated(entry.getLong("created"))
                    .setCreated_utc(entry.getLong("created_utc"));
            if (!String.valueOf(entry.get("likes")).equals("null")) {
                builder.setLikes(entry.getBoolean("likes"));
            }
            return builder.createLink();
        }
    }
}
