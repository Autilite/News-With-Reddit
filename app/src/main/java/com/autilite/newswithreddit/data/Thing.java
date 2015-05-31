package com.autilite.newswithreddit.data;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static class ThingFactory {

        public Thing makeThing(JSONObject object) throws JSONException {
            String kind = object.getString("kind");
            switch (kind){
                case "t1":
                    break;
                case "t3":
                    return makeLink(object);
                case "t4":
                    break;
                case "t5":
                    break;
                default:
                    return null;
            }
            return null;
        }

        private Thing makeLink(JSONObject object) throws JSONException {
            JSONObject entry = object.getJSONObject("data");
            Link.LinkBuilder builder = new Link.LinkBuilder()
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
