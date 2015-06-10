package com.autilite.newswithreddit.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by kelvin on 6/9/15.
 */
public class LoadThumbnail extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = LoadThumbnail.class.getName();

    private static ThumbnailCache mThumbnailCache = ThumbnailCache.getInstance();
    private ImageView mBitmap;

    public LoadThumbnail(ImageView bitmap) {
        this.mBitmap = bitmap;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mImage = mThumbnailCache.getBitmapFromCache(url);
        if (mImage != null) {
            Log.v(TAG, "Loaded thumbnail from cache: " + url);
        } else {
            try {
                InputStream in = new URL(url).openStream();
                mImage = BitmapFactory.decodeStream(in);
                mThumbnailCache.addBitmapToCache(url, mImage);
            } catch (IOException e) {
                Log.w(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
        return mImage;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mBitmap.setImageBitmap(bitmap);
    }

}
