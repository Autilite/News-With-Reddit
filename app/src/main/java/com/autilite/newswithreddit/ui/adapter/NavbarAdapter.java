package com.autilite.newswithreddit.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autilite.newswithreddit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 03/06/15.
 */
public class NavbarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_SUBREDDIT = 1;
    private List<NavbarItem> subreddits;
    private List<NavbarItem> headerItems;

    public static enum Type {
        FRONT_PAGE, ALL, RANDOM, SEARCH, SUBREDDIT
    }

    public NavbarAdapter(List<NavbarItem> subreddits) {
        this.subreddits = new ArrayList<>();
        headerItems = new ArrayList<>();
        this.subreddits = subreddits;

        headerItems.add(new NavbarItem("Front page", Type.FRONT_PAGE));
        headerItems.add(new NavbarItem("All", Type.ALL));
        headerItems.add(new NavbarItem("random", Type.RANDOM));
        headerItems.add(new NavbarItem("Search subreddit", Type.SEARCH));
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < headerItems.size()){
            return TYPE_HEADER;
        }
        return TYPE_SUBREDDIT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.subreddit_list_item, viewGroup, false);
        return new SubredditHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SubredditHolder) {
            ((SubredditHolder) holder).mSubreddit.setText(getSubreddit(position));
        }
        else throw new RuntimeException("Incorrect ViewHolder type");
    }

    @Override
    public int getItemCount() {
        return subreddits.size() + headerItems.size();
    }

    public String getSubreddit(int position) {
        // Since we have a header, need to subtract it for correct List indexing
        if (isSubreddit(position)) {
            return subreddits.get(position - headerItems.size()).title;
        } else {
            return headerItems.get(position).title;
        }
    }

    public boolean isSubreddit(int position) {
        return position >= headerItems.size() && position <= subreddits.size() + headerItems.size();
    }

    public NavbarItem getNavbarItem(int position) {
        if (0 <= position && position < headerItems.size()) {
            return headerItems.get(position);
        } else if (isSubreddit(position)) {
            return subreddits.get(position - headerItems.size());
        }
        return null;
    }

    public class SubredditHolder extends RecyclerView.ViewHolder {
        private TextView mSubreddit;

        public SubredditHolder(View itemView) {
            super(itemView);
            mSubreddit = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public final static class NavbarItem {
        public final String title;
        public final Type type;

        public NavbarItem(String title, Type type) {
            this.title = title;
            this.type = type;
        }
    }

}
