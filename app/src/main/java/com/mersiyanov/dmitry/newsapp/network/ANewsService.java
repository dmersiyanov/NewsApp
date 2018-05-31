package com.mersiyanov.dmitry.newsapp.network;

import com.mersiyanov.dmitry.newsapp.pojo.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ANewsService {

    @GET("api/v3/search")
    Single<NewsResponse> getNews(@Query("q") String query);

    @GET("api/v2/sources/search")
    Single<NewsResponse> getSources(@Query("q") String query);





}
