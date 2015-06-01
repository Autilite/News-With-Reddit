package com.autilite.newswithreddit.data;

import java.util.List;

/**
 * Created by kelvin on 5/30/15.
 */
public class Comment extends Thing { // t1, implements votable, created
    private int level;
    private String approved_by;
    private String author;
    private String author_flair_css_class;
    private String author_flare_text;
    private String banned_by;
    private String body;
    private String body_html;
    private String edited;  // special
    private int gilded;
    private boolean likes;
    private String link_author;
    private String link_id;
    private String link_title;
    private String link_url;
    private int num_reports;
    private String parent_id;
    private List<Comment> replies;     // List<thing> - list of replies to this comment
    // TODO more kind
    private boolean saved;
    private int score;
    private boolean score_hidden;
    private String subreddit;
    private String subreddit_id;
    private String distinguished; // null, moderator, admin, special
    private long created;
    private long created_utc;

    @Override
    protected void setKind() {
        kind = "t1";
    }

    private Comment(String id, String name, int level, String approved_by, String author, String author_flair_css_class, String author_flare_text, String banned_by, String body, String body_html, String edited, int gilded, boolean likes, String link_author, String link_id, String link_title, String link_url, int num_reports, String parent_id, List<Comment> replies, boolean saved, int score, boolean score_hidden, String subreddit, String subreddit_id, String distinguished, long created, long created_utc) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.approved_by = approved_by;
        this.author = author;
        this.author_flair_css_class = author_flair_css_class;
        this.author_flare_text = author_flare_text;
        this.banned_by = banned_by;
        this.body = body;
        this.body_html = body_html;
        this.edited = edited;
        this.gilded = gilded;
        this.likes = likes;
        this.link_author = link_author;
        this.link_id = link_id;
        this.link_title = link_title;
        this.link_url = link_url;
        this.num_reports = num_reports;
        this.parent_id = parent_id;
        this.replies = replies;
        this.saved = saved;
        this.score = score;
        this.score_hidden = score_hidden;
        this.subreddit = subreddit;
        this.subreddit_id = subreddit_id;
        this.distinguished = distinguished;
        this.created = created;
        this.created_utc = created_utc;
    }

    public int getLevel() {
        return level;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthor_flair_css_class() {
        return author_flair_css_class;
    }

    public String getAuthor_flare_text() {
        return author_flare_text;
    }

    public String getBanned_by() {
        return banned_by;
    }

    public String getBody() {
        return body;
    }

    public String getBody_html() {
        return body_html;
    }

    public String getEdited() {
        return edited;
    }

    public int getGilded() {
        return gilded;
    }

    public boolean isLikes() {
        return likes;
    }

    public String getLink_author() {
        return link_author;
    }

    public String getLink_id() {
        return link_id;
    }

    public String getLink_title() {
        return link_title;
    }

    public String getLink_url() {
        return link_url;
    }

    public int getNum_reports() {
        return num_reports;
    }

    public String getParent_id() {
        return parent_id;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public boolean isSaved() {
        return saved;
    }

    public int getScore() {
        return score;
    }

    public boolean isScore_hidden() {
        return score_hidden;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getSubreddit_id() {
        return subreddit_id;
    }

    public String getDistinguished() {
        return distinguished;
    }

    public long getCreated() {
        return created;
    }

    public long getCreated_utc() {
        return created_utc;
    }

    public static class CommentBuilder {
        private String id;
        private String name;
        private int level;
        private String approved_by;
        private String author;
        private String author_flair_css_class;
        private String author_flare_text;
        private String banned_by;
        private String body;
        private String body_html;
        private String edited;
        private int gilded;
        private boolean likes;
        private String link_author;
        private String link_id;
        private String link_title;
        private String link_url;
        private int num_reports;
        private String parent_id;
        private List<Comment> replies;
        private boolean saved;
        private int score;
        private boolean score_hidden;
        private String subreddit;
        private String subreddit_id;
        private String distinguished;
        private long created;
        private long created_utc;

        public CommentBuilder setLevel(int level) {
            this.level = level;
            return this;
        }

        public CommentBuilder setApproved_by(String approved_by) {
            this.approved_by = approved_by;
            return this;
        }

        public CommentBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public CommentBuilder setAuthor_flair_css_class(String author_flair_css_class) {
            this.author_flair_css_class = author_flair_css_class;
            return this;
        }

        public CommentBuilder setAuthor_flare_text(String author_flare_text) {
            this.author_flare_text = author_flare_text;
            return this;
        }

        public CommentBuilder setBanned_by(String banned_by) {
            this.banned_by = banned_by;
            return this;
        }

        public CommentBuilder setBody(String body) {
            this.body = body;
            return this;
        }

        public CommentBuilder setBody_html(String body_html) {
            this.body_html = body_html;
            return this;
        }

        public CommentBuilder setEdited(String edited) {
            this.edited = edited;
            return this;
        }

        public CommentBuilder setGilded(int gilded) {
            this.gilded = gilded;
            return this;
        }

        public CommentBuilder setLikes(boolean likes) {
            this.likes = likes;
            return this;
        }

        public CommentBuilder setLink_author(String link_author) {
            this.link_author = link_author;
            return this;
        }

        public CommentBuilder setLink_id(String link_id) {
            this.link_id = link_id;
            return this;
        }

        public CommentBuilder setLink_title(String link_title) {
            this.link_title = link_title;
            return this;
        }

        public CommentBuilder setLink_url(String link_url) {
            this.link_url = link_url;
            return this;
        }

        public CommentBuilder setNum_reports(int num_reports) {
            this.num_reports = num_reports;
            return this;
        }

        public CommentBuilder setParent_id(String parent_id) {
            this.parent_id = parent_id;
            return this;
        }

        public CommentBuilder setReplies(List<Comment> replies) {
            this.replies = replies;
            return this;
        }

        public CommentBuilder setSaved(boolean saved) {
            this.saved = saved;
            return this;
        }

        public CommentBuilder setScore(int score) {
            this.score = score;
            return this;
        }

        public CommentBuilder setScore_hidden(boolean score_hidden) {
            this.score_hidden = score_hidden;
            return this;
        }

        public CommentBuilder setSubreddit(String subreddit) {
            this.subreddit = subreddit;
            return this;
        }

        public CommentBuilder setSubreddit_id(String subreddit_id) {
            this.subreddit_id = subreddit_id;
            return this;
        }

        public CommentBuilder setDistinguished(String distinguished) {
            this.distinguished = distinguished;
            return this;
        }

        public CommentBuilder setCreated(long created) {
            this.created = created;
            return this;
        }

        public CommentBuilder setCreated_utc(long created_utc) {
            this.created_utc = created_utc;
            return this;
        }

        public CommentBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public CommentBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Comment createComment() {
            return new Comment(id, name, level, approved_by, author, author_flair_css_class, author_flare_text, banned_by, body, body_html, edited, gilded, likes, link_author, link_id, link_title, link_url, num_reports, parent_id, replies, saved, score, score_hidden, subreddit, subreddit_id, distinguished, created, created_utc);
        }

    }
}
