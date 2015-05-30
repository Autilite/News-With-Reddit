package com.autilite.newswithreddit;

import android.view.View;

/**
 * Created by kelvin on 5/29/15.
 */
public class Post {
    private String domain;
    private String subreddit;
    private String thumbnail;
    private String permalink;
    private String url;
    private String id;
    private String name;
    private String title;
    private String author;
    private int score;
    private int num_comments;
    private boolean visited;
    private boolean over_18;
    private boolean hidden;

    private Post(){}

    public String getDomain() {
        return domain;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getScore() {
        return score;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isOver_18() {
        return over_18;
    }

    public boolean isHidden() {
        return hidden;
    }

    public static class PostBuilder {
        private String domain;
        private String subreddit;
        private String thumbnail;
        private String permalink;
        private String url;
        private String id;
        private String name;
        private String title;
        private String author;
        private int score;
        private int num_comments;
        private boolean visited;
        private boolean over_18;
        private boolean hidden;

        public PostBuilder() {}

        public PostBuilder setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public PostBuilder setSubreddit(String subreddit) {
            this.subreddit = subreddit;
            return this;
        }

        public PostBuilder setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public PostBuilder setPermalink(String permalink) {
            this.permalink = permalink;
            return this;
        }

        public PostBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public PostBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public PostBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PostBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public PostBuilder setScore(int score) {
            this.score = score;
            return this;
        }

        public PostBuilder setNum_comments(int num_comments) {
            this.num_comments = num_comments;
            return this;
        }

        public PostBuilder setVisited(boolean visited) {
            this.visited = visited;
            return this;
        }

        public PostBuilder setOver_18(boolean over_18) {
            this.over_18 = over_18;
            return this;
        }

        public PostBuilder setHidden(boolean hidden) {
            this.hidden = hidden;
            return this;
        }

        public Post build () {
            Post post = new Post();
            post.domain = this.domain;
            post.subreddit = this.subreddit;
            post.thumbnail = this.thumbnail;
            post.permalink = this.permalink;
            post.url = this.url;
            post.id = this.id;
            post.name = this.name;
            post.title = this.title;
            post.author = this.author;
            post.score = this.score;
            post.num_comments = this.num_comments;
            post.visited = this.visited;
            post.over_18 = this.over_18;
            post.hidden = this.hidden;
            return post;
        }

    }
}
