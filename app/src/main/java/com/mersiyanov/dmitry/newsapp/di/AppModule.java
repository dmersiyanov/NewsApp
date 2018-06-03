package com.mersiyanov.dmitry.newsapp.di;

import com.mersiyanov.dmitry.newsapp.network.ApiHelper;
import com.mersiyanov.dmitry.newsapp.ui.news.NewsPresenter;
import com.mersiyanov.dmitry.newsapp.ui.news.NewsRepository;
import com.mersiyanov.dmitry.newsapp.ui.news.NewsScreenContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    ApiHelper provideApi() {
        return new ApiHelper();
    }

    @Provides
    @Singleton
    NewsScreenContract.Repo provideRepo(ApiHelper apiHelper) {
        return new NewsRepository(apiHelper);
    }

    @Provides
    @Singleton
    NewsScreenContract.Presenter providePresenter(NewsRepository repository) {
        return new NewsPresenter(repository);
    }
}
