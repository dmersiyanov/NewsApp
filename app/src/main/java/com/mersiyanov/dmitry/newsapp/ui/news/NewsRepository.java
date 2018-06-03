package com.mersiyanov.dmitry.newsapp.ui.news;

import com.mersiyanov.dmitry.newsapp.network.ApiHelper;
import com.mersiyanov.dmitry.newsapp.pojo.news.NewsResponse;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsRepository implements NewsScreenContract.Repo {

    private final ApiHelper apiHelper;
    private Single<NewsResponse> cache;

    public NewsRepository(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    @Override
    public Single<NewsResponse> load(String query) {
        return apiHelper.getApi().getNews(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    }
}
