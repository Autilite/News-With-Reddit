package com.autilite.newswithreddit.fetcher;

import com.autilite.newswithreddit.data.Thing;

import java.util.List;

/**
 * Created by kelvin on 01/06/15.
 */
public abstract class RedditFetcher<T extends Thing> {

    public final String REDDIT_BASE_URL = "http://www.reddit.com";
    public final String REDDIT_SUBREDDIT_BASE = "/r/";
    public final String REDDIT_USER_BASE = "/u/";
    public final String JSON_FORMAT = ".json";
    public final String paramAfter = "?after=";

    protected String url;
    protected String urlParams;
    protected String after;

    public RedditFetcher(String permalink) {
        this.url = REDDIT_BASE_URL + permalink;
        this.urlParams = permalink;
        after = "";
    }

    public String getUrl() {
        return url;
    }

    public abstract void generateUrl();

    public abstract List<T> fetch();

}
