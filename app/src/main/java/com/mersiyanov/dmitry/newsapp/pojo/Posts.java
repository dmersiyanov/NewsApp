package com.mersiyanov.dmitry.newsapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posts {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("newsItem")
    @Expose
    private java.util.List<NewsItem> newsItem = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public java.util.List<NewsItem> getNewsItem() {
        return newsItem;
    }

    public void setNewsItem(java.util.List<NewsItem> newsItem) {
        this.newsItem = newsItem;
    }

}