package com.mersiyanov.dmitry.newsapp;

import android.app.Application;

import com.mersiyanov.dmitry.newsapp.di.AppComponent;
import com.mersiyanov.dmitry.newsapp.di.DaggerAppComponent;

public class NewsApp extends Application {

    public static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.create();
    }
}
