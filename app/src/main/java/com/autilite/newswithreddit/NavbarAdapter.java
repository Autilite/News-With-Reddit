package com.autilite.newswithreddit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kelvin on 03/06/15.
 */
public class NavbarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_SUBREDDIT = 0;
    private List<String> subreddits;

    public NavbarAdapter(List<String> subreddits) {
        this.subreddits = subreddits;
    }

    @Override
    public int getItemViewType(int position) {
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
        ((SubredditHolder) holder).mSubreddit.setText(subreddits.get(position));
    }

    @Override
    public int getItemCount() {
        return subreddits.size();
    }

    public String getSubreddit(int position) {
        return subreddits.get(position);
    }

    public class SubredditHolder extends RecyclerView.ViewHolder {
        private TextView mSubreddit;

        public SubredditHolder(View itemView) {
            super(itemView);
            mSubreddit = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

}
