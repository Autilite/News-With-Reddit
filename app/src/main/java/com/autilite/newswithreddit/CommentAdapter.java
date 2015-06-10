package com.autilite.newswithreddit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autilite.newswithreddit.data.Comment;
import com.autilite.newswithreddit.data.Link;
import com.autilite.newswithreddit.util.LoadThumbnail;
import com.autilite.newswithreddit.util.ThumbnailUtil;

import java.util.List;

/**
 * Created by kelvin on 03/06/15.
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LINK = 0;
    private static final int TYPE_COMMENT = 1;
    private static final String TAG = CommentAdapter.class.getName();
    private List<Comment> comments;
    private Link link;
    private Context context;

    private String author;

    public CommentAdapter(Context context, Link link, List<Comment> comments) {
        // Need context for the padding on the comment level
        this.comments = comments;
        this.link = link;
        this.context = context;

        author = link.getAuthor();
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

            CharSequence body = removeTrailingLines(Html.fromHtml(com.getBody_html()));
            commentHolder.mBody.setText(body);
            commentHolder.mBody.setMovementMethod(LinkMovementMethod.getInstance());

            String comAuthor = com.getAuthor();

            // Set author text
            commentHolder.mAuthor.setText(comAuthor);
            int backResid = -1;
            int colResid = -1;
            // Set author distinguished style
            String distinguished = com.getDistinguished();
            switch(distinguished) {
                case "null":
                    boolean eq = author.equals(comAuthor);
                    if (eq) {
                        backResid = R.drawable.distinguished_submitter;
                        colResid = R.color.link_submitter;
                    }
                    break;
                case "moderator":
                    backResid = R.drawable.distinguished_moderator;
                    colResid = R.color.link_moderator;
                    break;
                case "admin":
                    backResid = R.drawable.distinguished_admin;
                    colResid = R.color.link_admin;
                    break;
                case "special":
                    // TODO
                    break;
                default:
                    Log.e(TAG, "Comment has invalid \"distinguished\" value" );
                    break;
            }
            if (backResid != -1) {
                commentHolder.mAuthor.setBackgroundResource(backResid);
                commentHolder.mAuthor.setTextColor(context.getResources().getColor(colResid));
            } else {
                commentHolder.mAuthor.setBackgroundResource(0);
                commentHolder.mAuthor.setTextColor(context.getResources().getColor(R.color.link_default));
            }
            // Set score
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

            // Get thumbnail url and parse it
            String thumbnailUrl = link.getThumbnail();
            int drawable;
            if ((drawable = ThumbnailUtil.getStaticThumbnail(thumbnailUrl)) != -1) {
                linkHolder.mThumbnail.setVisibility(View.VISIBLE);
                linkHolder.mThumbnail.setImageResource(drawable);
            } else if (!thumbnailUrl.equals("")) {
                // Download the the thumbnail and set it when done
                linkHolder.mThumbnail.setVisibility(View.VISIBLE);
                new LoadThumbnail((ImageView) linkHolder.mThumbnail.findViewById(R.id.link_thumbnail)).
                        execute(thumbnailUrl);
            } else {
                linkHolder.mThumbnail.setVisibility(View.GONE);
                linkHolder.mThumbnail.setImageBitmap(null);
            }

            // Set details
            linkHolder.mTitle.setText(link.getTitle());
            linkHolder.mStats.setText(String.valueOf(link.getNum_comments()) + " comments" + delim +
                    link.getScore() + " pts");
            linkHolder.mDetails.setText(link.getAuthor() + delim +
                    link.getSubreddit() + delim +
                    link.getDomain());

            // Display self text only if not empty to hide the border
            if (!link.getSelftext().equals("")) {
                CharSequence body = removeTrailingLines(Html.fromHtml(link.getSelftext_html()));
                linkHolder.mBody.setText(body);
                linkHolder.mBody.setMovementMethod(LinkMovementMethod.getInstance());
                linkHolder.mBody.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Removes the trailing two new lines from the text
     * @param text
     *      The text to trim
     * @return
     *      The text without the last two blank lines or the original text
     */
    private CharSequence removeTrailingLines(CharSequence text) {
        if (text.charAt(text.length() - 1) == '\n' && text.charAt(text.length() - 2) == '\n') {
            return text.subSequence(0, text.length() - 2);
        } else return text;
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
        private ImageView mThumbnail;
        private TextView mTitle;
        private TextView mStats;
        private TextView mDetails;
        private TextView mBody;

        public LinkHolder(View itemView) {
            super(itemView);
            mThumbnail = (ImageView) itemView.findViewById(R.id.link_thumbnail);
            mTitle = (TextView) itemView.findViewById(R.id.link_title);
            mStats = (TextView) itemView.findViewById(R.id.link_stats);
            mDetails = (TextView) itemView.findViewById(R.id.link_author);
            mBody = (TextView) itemView.findViewById(R.id.link_body);
        }
    }

}
