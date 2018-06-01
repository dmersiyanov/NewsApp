package com.mersiyanov.dmitry.newsapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SourcesResponse {

    @SerializedName("feeds")
    @Expose
    private List<SourceItem> feeds = null;
    @SerializedName("streams")
    @Expose
    private List<Stream> streams = null;

    public List<SourceItem> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<SourceItem> feeds) {
        this.feeds = feeds;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

}
