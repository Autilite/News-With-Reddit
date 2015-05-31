package com.autilite.newswithreddit.data;

import android.util.Log;

/**
 * Created by kelvin on 5/30/15.
 */
public class Link extends Thing { // t3, implements votable, created
    private String author;
    private String author_flair_css_class;
    private String author_flare_text;
    private String clicked;
    private String domain;
    private boolean hidden;
    private boolean is_self;
    private boolean likes;  // true up; false down; null none
    private String link_flair_css_class;
    private String link_flair_text;
    private Object media; // streaming video. info about video and origin
    private Object media_embed; // technical embed specific info
    private int num_comments;
    private boolean over_18;
    private String permalink;
    private boolean saved;
    private int score;
    private String selftext;
    private String selftext_html;
    private String subreddit;
    private String subreddit_id;
    private String thumbnail;
    private String title;
    private String url;
    private long edited;
    private String distinguished; // null, moderator, admin, special
    private boolean stickied;
    private long created;
    private long created_utc;

    public String getAuthor() {
        return author;
    }

    public String getAuthor_flair_css_class() {
        return author_flair_css_class;
    }

    public String getAuthor_flare_text() {
        return author_flare_text;
    }

    public String getClicked() {
        return clicked;
    }

    public String getDomain() {
        return domain;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isIs_self() {
        return is_self;
    }

    public boolean isLikes() {
        return likes;
    }

    public String getLink_flair_css_class() {
        return link_flair_css_class;
    }

    public String getLink_flair_text() {
        return link_flair_text;
    }

    public Object getMedia() {
        return media;
    }

    public Object getMedia_embed() {
        return media_embed;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public boolean isOver_18() {
        return over_18;
    }

    public String getPermalink() {
        return permalink;
    }

    public boolean isSaved() {
        return saved;
    }

    public int getScore() {
        return score;
    }

    public String getSelftext() {
        return selftext;
    }

    public String getSelftext_html() {
        return selftext_html;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getSubreddit_id() {
        return subreddit_id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public long getEdited() {
        return edited;
    }

    public String getDistinguished() {
        return distinguished;
    }

    public boolean isStickied() {
        return stickied;
    }

    public long getCreated() {
        return created;
    }

    public long getCreated_utc() {
        return created_utc;
    }

    @Override
    protected void setKind() {
        kind = "t3";
    }

    @Override
    public String toString() {
        return "Link{" +
                "author='" + author + '\'' +
                ", author_flair_css_class='" + author_flair_css_class + '\'' +
                ", author_flare_text='" + author_flare_text + '\'' +
                ", clicked='" + clicked + '\'' +
                ", domain='" + domain + '\'' +
                ", hidden=" + hidden +
                ", is_self=" + is_self +
                ", likes=" + likes +
                ", link_flair_css_class='" + link_flair_css_class + '\'' +
                ", link_flair_text='" + link_flair_text + '\'' +
                ", media=" + media +
                ", media_embed=" + media_embed +
                ", num_comments=" + num_comments +
                ", over_18=" + over_18 +
                ", permalink='" + permalink + '\'' +
                ", saved=" + saved +
                ", score=" + score +
                ", selftext='" + selftext + '\'' +
                ", selftext_html='" + selftext_html + '\'' +
                ", subreddit='" + subreddit + '\'' +
                ", subreddit_id='" + subreddit_id + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", edited=" + edited +
                ", distinguished='" + distinguished + '\'' +
                ", stickied=" + stickied +
                ", created=" + created +
                ", created_utc=" + created_utc +
                '}';
    }

    public static class LinkBuilder {

        private String author;
        private String author_flair_css_class;
        private String author_flare_text;
        private String clicked;
        private String domain;
        private boolean hidden;
        private boolean is_self;
        private boolean likes;
        private String link_flair_css_class;
        private String link_flair_text;
        private Object media;
        private Object media_embed;
        private int num_comments;
        private boolean over_18;
        private String permalink;
        private boolean saved;
        private int score;
        private String selftext;
        private String selftext_html;
        private String subreddit;
        private String subreddit_id;
        private String thumbnail;
        private String title;
        private String url;
        private long edited;
        private String distinguished;
        private boolean stickied;
        private long created;
        private long created_utc;

        public LinkBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public LinkBuilder setAuthor_flair_css_class(String author_flair_css_class) {
            this.author_flair_css_class = author_flair_css_class;
            return this;
        }

        public LinkBuilder setAuthor_flare_text(String author_flare_text) {
            this.author_flare_text = author_flare_text;
            return this;
        }

        public LinkBuilder setClicked(String clicked) {
            this.clicked = clicked;
            return this;
        }

        public LinkBuilder setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public LinkBuilder setHidden(boolean hidden) {
            this.hidden = hidden;
            return this;
        }

        public LinkBuilder setIs_self(boolean is_self) {
            this.is_self = is_self;
            return this;
        }

        public LinkBuilder setLikes(boolean likes) {
            this.likes = likes;
            return this;
        }

        public LinkBuilder setLink_flair_css_class(String link_flair_css_class) {
            this.link_flair_css_class = link_flair_css_class;
            return this;
        }

        public LinkBuilder setLink_flair_text(String link_flair_text) {
            this.link_flair_text = link_flair_text;
            return this;
        }

        public LinkBuilder setMedia(Object media) {
            this.media = media;
            return this;
        }

        public LinkBuilder setMedia_embed(Object media_embed) {
            this.media_embed = media_embed;
            return this;
        }

        public LinkBuilder setNum_comments(int num_comments) {
            this.num_comments = num_comments;
            return this;
        }

        public LinkBuilder setOver_18(boolean over_18) {
            this.over_18 = over_18;
            return this;
        }

        public LinkBuilder setPermalink(String permalink) {
            this.permalink = permalink;
            return this;
        }

        public LinkBuilder setSaved(boolean saved) {
            this.saved = saved;
            return this;
        }

        public LinkBuilder setScore(int score) {
            this.score = score;
            return this;
        }

        public LinkBuilder setSelftext(String selftext) {
            this.selftext = selftext;
            return this;
        }

        public LinkBuilder setSelftext_html(String selftext_html) {
            this.selftext_html = selftext_html;
            return this;
        }

        public LinkBuilder setSubreddit(String subreddit) {
            this.subreddit = subreddit;
            return this;
        }

        public LinkBuilder setSubreddit_id(String subreddit_id) {
            this.subreddit_id = subreddit_id;
            return this;
        }

        public LinkBuilder setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public LinkBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public LinkBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public LinkBuilder setEdited(long edited) {
            this.edited = edited;
            return this;
        }

        public LinkBuilder setDistinguished(String distinguished) {
            this.distinguished = distinguished;
            return this;
        }

        public LinkBuilder setStickied(boolean stickied) {
            this.stickied = stickied;
            return this;
        }

        public LinkBuilder setCreated(long created) {
            this.created = created;
            return this;
        }

        public LinkBuilder setCreated_utc(long created_utc) {
            this.created_utc = created_utc;
            return this;
        }

        public Link createLink() {
            Link link = new Link();
            link.author = this.author;
            link.author_flair_css_class = this.author_flair_css_class;
            link.author_flare_text = this.author_flare_text;
            link.clicked = this.clicked;
            link.domain = this.domain;
            link.hidden = this.hidden;
            link.is_self = this.is_self;
            link.likes = this.likes;
            link.link_flair_css_class = this.link_flair_css_class;
            link.link_flair_text = this.link_flair_text;
            link.media = this.media;
            link.media_embed = this.media_embed;
            link.num_comments = this.num_comments;
            link.over_18 = this.over_18;
            link.permalink = this.permalink;
            link.saved = this.saved;
            link.score = this.score;
            link.selftext = this.selftext;
            link.selftext_html = this.selftext_html;
            link.subreddit = this.subreddit;
            link.subreddit_id = this.subreddit_id;
            link.thumbnail = this.thumbnail;
            link.title = this.title;
            link.url = this.url;
            link.edited = this.edited;
            link.distinguished = this.distinguished;
            link.stickied = this.stickied;
            link.created = this.created;
            link.created_utc = this.created_utc;
            return link;
        }
    }

}
