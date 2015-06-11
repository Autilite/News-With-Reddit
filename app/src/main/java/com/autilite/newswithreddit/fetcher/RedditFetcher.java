package com.autilite.newswithreddit.fetcher;

import com.autilite.newswithreddit.data.Thing;

import java.util.List;

/**
 * Created by kelvin on 01/06/15.
 */
public abstract class RedditFetcher<T extends Thing> {

    protected String url;

    public abstract void generateUrl();

    public abstract List<T> fetch();

}
