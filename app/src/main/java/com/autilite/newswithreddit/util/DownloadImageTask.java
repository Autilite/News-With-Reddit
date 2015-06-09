package com.autilite.newswithreddit.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by kelvin on 6/9/15.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = DownloadImageTask.class.getName();

    private ImageView mBitmap;

    public DownloadImageTask(ImageView bitmap) {
        this.mBitmap = bitmap;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap mImage = null;
        try {
            InputStream in = new URL(url).openStream();
            mImage = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.w(TAG, e.getMessage());
            e.printStackTrace();
        }
        return mImage;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        mBitmap.setImageBitmap(bitmap);
    }

}
