package com.mersiyanov.dmitry.newsapp.ui.news;

import com.mersiyanov.dmitry.newsapp.pojo.news.NewsItem;
import com.mersiyanov.dmitry.newsapp.pojo.news.NewsResponse;
import com.mersiyanov.dmitry.newsapp.pojo.news.Pages;

import java.util.List;

import io.reactivex.Single;

public interface NewsScreenContract {

    interface View {

        void showLoading();

        void showError();

        void showData(List<NewsItem> newsItems, Pages pages);

        void setPresenter(Presenter presenter);
    }

    interface Presenter {

        void attachView(View view);

        void detachView();

        void load(String query);
    }

    interface Repo {

        Single<NewsResponse> load(String query);
    }

}
