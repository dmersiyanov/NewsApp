package com.mersiyanov.dmitry.newsapp.ui.news;

import android.support.annotation.NonNull;

import com.mersiyanov.dmitry.newsapp.pojo.news.NewsResponse;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class NewsPresenter implements NewsScreenContract.Presenter {

    private NewsScreenContract.View view;
    private NewsScreenContract.Repo repo;

    public NewsPresenter(NewsScreenContract.Repo repo, @NonNull NewsScreenContract.View view) {
        this.repo = repo;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void attachView(NewsScreenContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void load(String query) {
        if(view != null) {
            view.showLoading();
            repo.load(query).subscribe(new SingleObserver<NewsResponse>() {
                @Override
                public void onSubscribe(Disposable d) { }

                @Override
                public void onSuccess(NewsResponse newsResponse) {
                    view.showData(newsResponse.getPosts().getNewsItem());
                }

                @Override
                public void onError(Throwable e) {
                    view.showError();
                }
            });
        }

    }
}
