package com.autilite.newswithreddit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autilite.newswithreddit.data.Link;
import com.autilite.newswithreddit.util.DownloadImageTask;
import com.autilite.newswithreddit.util.ThumbnailUtil;

import java.util.List;

/**
 * Created by kelvin on 03/06/15.
 */
public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkHolder>{
    private List<Link> links;
    private LinkListFragment.LinkItemCallbacks linkListener;

    public LinkAdapter(List<Link> links) {
        this.links = links;
    }

    @Override
    public LinkHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.fragment_link_item, viewGroup, false);
        return new LinkHolder(view);
    }

    @Override
    public void onBindViewHolder(LinkHolder linkHolder, int i) {
        String delim = " - ";
        Link link = links.get(i);

        // Get thumbnail url and parse it
        String thumbnailUrl = link.getThumbnail();
        int drawable;
        if ((drawable = ThumbnailUtil.getStaticThumbnail(thumbnailUrl)) != -1) {
            linkHolder.mThumbnail.setVisibility(View.VISIBLE);
            linkHolder.mThumbnail.setImageResource(drawable);
        } else if (!thumbnailUrl.equals("")) {
            // Download the the thumbnail and set it when done
            linkHolder.mThumbnail.setVisibility(View.VISIBLE);
            new DownloadImageTask((ImageView) linkHolder.mThumbnail.findViewById(R.id.link_thumbnail)).
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
        linkHolder.bindLink(link);
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public void setOnLinkClickListener(LinkListFragment.LinkItemCallbacks linkListener) {
        this.linkListener = linkListener;
    }

    public class LinkHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView mThumbnail;
        private final TextView mTitle;
        private final TextView mStats;
        private final TextView mDetails;
        private Link link;

        public LinkHolder(View itemView) {
            super(itemView);
            mThumbnail = (ImageView) itemView.findViewById(R.id.link_thumbnail);
            mTitle = (TextView) itemView.findViewById(R.id.link_title);
            mStats = (TextView) itemView.findViewById(R.id.link_stats);
            mDetails = (TextView) itemView.findViewById(R.id.link_author);
            itemView.setOnClickListener(this);
        }

        public void bindLink(Link link) {
            this.link = link;
        }

        @Override
        public void onClick(View v) {
            if (link != null && linkListener != null) {
                linkListener.onLinkSelect(link);
            }
        }
    }
}
