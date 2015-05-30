package com.autilite.newswithreddit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, PostListFragment.PostItemCallbacks,
        PostCommentFragment.PostCommentListener{

    public static final String COMMENT_QUERY = "com.autilite.newswithreddit.COMMENT";
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
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(String subreddit) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.action_bar_container, StatusBarFragment.newInstance(subreddit))
                .replace(R.id.container, PostListFragment.newInstance(subreddit))
                .commit();
    }

    @Override
    public void onPostSelect(Post post) {
        Log.i("POST", post.getTitle() + "(" + post.getSubreddit() + ") was selected.");
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra(COMMENT_QUERY, post.getPermalink());
        startActivity(intent);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class StatusBarFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SUBREDDIT = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static StatusBarFragment newInstance(String subreddit) {
            StatusBarFragment fragment = new StatusBarFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SUBREDDIT, subreddit);
            fragment.setArguments(args);
            return fragment;
        }

        public StatusBarFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getString(ARG_SUBREDDIT));
        }
    }

}
