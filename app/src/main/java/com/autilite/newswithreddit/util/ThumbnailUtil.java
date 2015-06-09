package com.autilite.newswithreddit.util;

import com.autilite.newswithreddit.R;

/**
 * Created by kelvin on 6/9/15.
 */
public class ThumbnailUtil {
    /**
     * Returns the resource id of the static reddit thumbnails.
     * @param thumbnail
     *      The thumbnail resource to retrieve
     * @return
     *      The resource id of the thumbnail or -1 if it doesn't apply
     */
    public static int getStaticThumbnail(String thumbnail) {
        switch (thumbnail) {
            case "nsfw":
                return R.drawable.nsfw2;
            case "self":
            case "default":
                return R.drawable.self_default2;
            case "":
                return R.drawable.noimage;
            default:
                return -1;
        }
    }
}
