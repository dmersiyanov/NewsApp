package com.mersiyanov.dmitry.newsapp.pojo.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsItem {

    @SerializedName("feed")
    @Expose
    private Feed feed;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("ts")
    @Expose
    private Integer ts;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("slug")
    @Expose
    private String slug;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

}