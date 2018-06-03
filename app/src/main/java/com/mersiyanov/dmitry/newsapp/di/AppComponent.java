package com.mersiyanov.dmitry.newsapp.di;

import com.mersiyanov.dmitry.newsapp.ui.MainActivity;
import com.mersiyanov.dmitry.newsapp.ui.news.NewsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent  {

    void injectsAct(MainActivity target);
    void injectsFrag(NewsFragment target);
}
