package com.autilite.newswithreddit.fetcher;

import com.autilite.newswithreddit.data.Thing;

import java.util.List;

/**
 * Created by kelvin on 01/06/15.
 */
public abstract class RedditFetcher {

    public final String REDDIT_BASE_URL = "http://www.reddit.com";
    public final String REDDIT_SUBREDDIT_BASE = "/r/";
    public final String REDDIT_USER_BASE = "/u/";
    public final String JSON_FORMAT = ".json";

    protected String url;
    protected String urlParams;

    public RedditFetcher(String permalink) {
        this.url = REDDIT_BASE_URL + permalink;
        this.urlParams = permalink;
    }

    public String getUrl() {
        return url;
    }

    public abstract void generateUrl();

}
