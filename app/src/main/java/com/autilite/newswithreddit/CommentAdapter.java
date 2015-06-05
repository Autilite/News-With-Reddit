package com.autilite.newswithreddit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autilite.newswithreddit.data.Comment;
import com.autilite.newswithreddit.data.Link;

import java.util.List;

/**
 * Created by kelvin on 03/06/15.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LINK = 0;
    private static final int TYPE_COMMENT = 1;
    private List<Comment> comments;
    private Link link;
    private Context context;

    public CommentAdapter(Context context, Link link, List<Comment> comments) {
        // Need context for the padding on the comment level
        this.comments = comments;
        this.link = link;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return isPositionLink(position) ? TYPE_LINK : TYPE_COMMENT;
    }

    private boolean isPositionLink(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == TYPE_LINK) {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.fragment_link_item, viewGroup, false);
            return new LinkHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.fragment_comment_item, viewGroup, false);
            return new CommentHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommentHolder) {
            Comment com = comments.get(position-1);
            CommentHolder commentHolder = (CommentHolder) holder;

            commentHolder.mBody.setText(com.getBody());
            commentHolder.mAuthor.setText(com.getAuthor());
            if (com.isScore_hidden()) {
                commentHolder.mPoints.setText(R.string.score_hidden);
            } else {
                commentHolder.mPoints.setText(com.getScore() + " points");
            }
            // Set padding to simulate nested levels
            int padding = context.getResources().getDimensionPixelSize(R.dimen.comment_item_padding);
            int paddingLeft = context.getResources().getDimensionPixelSize(R.dimen.comment_item_level_padding)
                    * com.getLevel() + padding;
            commentHolder.itemView.setPadding(paddingLeft, padding, padding, padding);
        } else if (holder instanceof LinkHolder) {
            String delim = " - ";
            LinkHolder linkHolder = (LinkHolder) holder;
            linkHolder.mTitle.setText(link.getTitle());
            linkHolder.mStats.setText(String.valueOf(link.getNum_comments()) + " comments" + delim +
                    link.getScore() + " pts");
            linkHolder.mDetails.setText(link.getAuthor() + delim +
                    link.getSubreddit() + delim +
                    link.getDomain());
        }
    }

    @Override
    public int getItemCount() {
        // Add 1 for the link info
        return comments.size() + 1;
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        private TextView mAuthor;
        private TextView mPoints;
        private TextView mBody;
        private TextView mTime;

        public CommentHolder(View itemView) {
            super(itemView);
            mAuthor = (TextView) itemView.findViewById(R.id.comment_author);
            mPoints = (TextView) itemView.findViewById(R.id.comment_points);
            mBody = (TextView) itemView.findViewById(R.id.comment_body);
            mTime = (TextView) itemView.findViewById(R.id.comment_time_ago);
        }
    }

    public class LinkHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mStats;
        private TextView mDetails;

        public LinkHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.link_title);
            mStats = (TextView) itemView.findViewById(R.id.link_stats);
            mDetails = (TextView) itemView.findViewById(R.id.link_author);
        }
    }

}