package com.autilite.newswithreddit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class CommentActivity extends ActionBarActivity
        implements LinkCommentFragment.LinkCommentListener {

    private String mId;
    private String mSubreddit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mId = intent.getStringExtra(MainActivity.COMMENT_QUERY);
        mSubreddit = intent.getStringExtra(MainActivity.COMMENT_SUBREDDIT);
        setContentView(R.layout.activity_comment);

        if (findViewById(R.id.comment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            LinkCommentFragment fragment = LinkCommentFragment.newInstance(mSubreddit, mId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.comment_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mSubreddit);
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
