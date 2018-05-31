package com.mersiyanov.dmitry.newsapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mersiyanov.dmitry.newsapp.R;
import com.mersiyanov.dmitry.newsapp.network.ApiHelper;
import com.mersiyanov.dmitry.newsapp.pojo.NewsItem;
import com.mersiyanov.dmitry.newsapp.pojo.NewsResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsFragment extends Fragment {

    private RecyclerView news_rv;
    private NewsAdapter adapter;
    private List<NewsItem> items = new ArrayList<>();
    private ApiHelper apiHelper = new ApiHelper();


    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        news_rv = rootView.findViewById(R.id.rv_news);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        news_rv.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(clickListener);
        news_rv.setAdapter(adapter);

        loadData();

        return rootView;
    }

    private void loadData() {
        apiHelper.getApi().getNews("дом")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NewsResponse newsResponse) {
                        items = newsResponse.getPosts().getNewsItem();
                        adapter.setItems(items);
//                        Toast.makeText(getContext(), items.get(0).getTitle(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


        private final NewsAdapter.OnNewsClickListener clickListener = new NewsAdapter.OnNewsClickListener() {

            @Override
            public void onClick(NewsItem item) {
                Toast.makeText(getContext(), "Клик на новость #" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        };

    }

