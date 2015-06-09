package com.autilite.newswithreddit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kelvin on 03/06/15.
 */
public class NavbarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SEARCH = 0;
    private static final int TYPE_SUBREDDIT = 1;
    private List<String> subreddits;

    public NavbarAdapter(List<String> subreddits) {
        this.subreddits = subreddits;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_SEARCH){
            return position;
        }
        return TYPE_SUBREDDIT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == TYPE_SEARCH) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.subreddit_list_header, viewGroup, false);
            return new HeaderHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.subreddit_list_item, viewGroup, false);
            return new SubredditHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SubredditHolder) {
            ((SubredditHolder) holder).mSubreddit.setText(getSubreddit(position));
        } else if (holder instanceof HeaderHolder) {
            HeaderHolder header = (HeaderHolder) holder;
            header.searchSubreddit.setText("Search subreddit");
        }
        else throw new RuntimeException("Incorrect ViewHolder type");
    }

    private int getHeaderSize() {
        return 1;
    }

    @Override
    public int getItemCount() {
        return subreddits.size() + getHeaderSize();
    }

    public String getSubreddit(int position) {
        // Since we have a header, need to subtract it for correct List indexing
        if (isSubreddit(position)) {
            return subreddits.get(position - getHeaderSize());
        } else {
            return "";
        }
    }

    public boolean isSearch(int position) {
        return position == TYPE_SEARCH;
    }

    public boolean isSubreddit(int position) {
        return position > 0 && position <= subreddits.size();
    }

    public class SubredditHolder extends RecyclerView.ViewHolder {
        private TextView mSubreddit;

        public SubredditHolder(View itemView) {
            super(itemView);
            mSubreddit = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        private Button searchSubreddit;

        public HeaderHolder(View itemView) {
            super(itemView);
            searchSubreddit = (Button) itemView.findViewById(R.id.search_subreddit);
        }
    }

}
