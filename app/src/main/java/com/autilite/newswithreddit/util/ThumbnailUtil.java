package com.autilite.newswithreddit.util;

/**
 * Created by kelvin on 6/9/15.
 */
public class ThumbnailUtil {
    public static String parseThumbnail(String thumbnail) {
        // TODO
        switch (thumbnail) {
            case "nsfw":
            case "self":
            case "default":
                return "";
            default:
                return thumbnail;
        }
    }
}
