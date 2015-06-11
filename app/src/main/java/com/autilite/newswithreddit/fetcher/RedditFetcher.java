package com.autilite.newswithreddit.fetcher;

import com.autilite.newswithreddit.data.Thing;

import java.util.List;

/**
 * Created by kelvin on 01/06/15.
 */
public abstract class RedditFetcher<T extends Thing> {
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

    protected String url;

    public abstract void generateUrl();

    public abstract List<T> fetch();

}
