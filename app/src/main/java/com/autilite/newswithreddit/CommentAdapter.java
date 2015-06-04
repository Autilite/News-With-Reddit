package com.autilite.newswithreddit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autilite.newswithreddit.data.Comment;

import java.util.List;

/**
 * Created by kelvin on 03/06/15.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private List<Comment> comments;
    private Context context;

    public CommentAdapter(Context context, List<Comment> comments) {
        // Need context for the padding on the comment level
        this.comments = comments;
        this.context = context;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.fragment_comment_item, viewGroup, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder commentHolder, int i) {
        Comment com = comments.get(i);

        commentHolder.mBody.setText(com.getBody());
        commentHolder.mAuthor.setText(com.getAuthor());
        if (com.isScore_hidden()) {
            commentHolder.mPoints.setText(R.string.score_hidden);
        } else {
            commentHolder.mPoints.setText(com.getScore() + " points");
        }
        int padding = context.getResources().getDimensionPixelSize(R.dimen.comment_item_padding);
        int paddingLeft = context.getResources().getDimensionPixelSize(R.dimen.comment_item_level_padding)
                * com.getLevel() + padding;
        commentHolder.itemView.setPadding(paddingLeft, padding, padding, padding);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        private final TextView mAuthor;
        private final TextView mPoints;
        private final TextView mBody;
        private final TextView mTime;

        public CommentHolder(View itemView) {
            super(itemView);
            mAuthor = (TextView) itemView.findViewById(R.id.comment_author);
            mPoints = (TextView) itemView.findViewById(R.id.comment_points);
            mBody = (TextView) itemView.findViewById(R.id.comment_body);
            mTime = (TextView) itemView.findViewById(R.id.comment_time_ago);
        }
    }
}
