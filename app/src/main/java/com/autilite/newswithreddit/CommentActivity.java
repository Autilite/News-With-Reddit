package com.autilite.newswithreddit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class CommentActivity extends ActionBarActivity
        implements PostCommentFragment.PostCommentListener {

    private String mPermaUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mPermaUrl = intent.getStringExtra(MainActivity.COMMENT_QUERY);
        setContentView(R.layout.activity_comment);

        if (findViewById(R.id.comment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            PostCommentFragment fragment = PostCommentFragment.newInstance(mPermaUrl);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.comment_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
