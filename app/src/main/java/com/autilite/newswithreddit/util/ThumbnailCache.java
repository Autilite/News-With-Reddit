package com.autilite.newswithreddit.util;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by kelvin on 6/9/15.
 */
public class ThumbnailCache extends LruCache<String, Bitmap> {
    private static ThumbnailCache instance;
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    private ThumbnailCache(int maxSize) {
        super(maxSize);
    }

    public static ThumbnailCache getInstance() {
        if (instance == null) {
            int maxMemory = (int) Runtime.getRuntime().maxMemory();
            int cacheSize = maxMemory / 8; // use 1/8th of heap size
            instance = new ThumbnailCache(cacheSize);
        }
        return instance;
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        if (get(key) == null) {
            put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String key) {
        return get(key);
    }

}
