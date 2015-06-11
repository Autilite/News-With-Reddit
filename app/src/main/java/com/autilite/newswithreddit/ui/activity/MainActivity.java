package com.autilite.newswithreddit.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.autilite.newswithreddit.ui.fragment.LinkCommentFragment;
import com.autilite.newswithreddit.ui.fragment.LinkListFragment;
import com.autilite.newswithreddit.ui.fragment.NavigationDrawerFragment;
import com.autilite.newswithreddit.R;
import com.autilite.newswithreddit.data.Link;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, LinkListFragment.LinkItemCallbacks,
        LinkCommentFragment.LinkCommentListener {

    private static final String TAG = MainActivity.class.getName();
    public static final String COMMENT_QUERY = "com.autilite.newswithreddit.COMMENT_ID";
    public static final String COMMENT_SUBREDDIT = "com.autilite.newswithreddit.COMMENT_SUBREDDIT";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onSubredditSelected(String subreddit) {
        Log.i(TAG, "Subreddit \"" + subreddit + "\" selected");
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, LinkListFragment.newInstance(subreddit))
                .commit();
        onSectionAttached(subreddit);
    }

    @Override
    public void onLinkSelect(Link link) {
        Log.i(TAG, link.getTitle() + " (" + link.getSubreddit() + ") was selected.\nPermalink: "
                + link.getPermalink());
        Intent comment = new Intent(this, CommentActivity.class);
        comment.putExtra(COMMENT_QUERY, link.getId());
        comment.putExtra(COMMENT_SUBREDDIT, link.getSubreddit());
        startActivity(comment);
    }

    @Override
    public void onThumbnailSelect(Link link) {
        Log.i(TAG, link.getTitle() + " (" + link.getSubreddit() + ") thumbnail was selected.\n" +
                "Permalink: " + link.getPermalink());
        if (isSelfLink(link)) {
            onLinkSelect(link);
        } else {
            String url = link.getUrl();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browser);
        }
    }

    private boolean isSelfLink(Link link) {
        String url = link.getUrl();
        return url.matches("^(https?://)?(www.)?reddit.com/r/[\\w]*/comments/[\\w]*(/?[\\w_\\d]*/?)?$");
    }

    public void onSectionAttached(String subreddit) {
        mTitle = subreddit;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
        // TODO
    }

}
