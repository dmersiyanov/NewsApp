package com.mersiyanov.dmitry.newsapp.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mersiyanov.dmitry.newsapp.R;
import com.mersiyanov.dmitry.newsapp.network.ApiHelper;
import com.mersiyanov.dmitry.newsapp.pojo.news.NewsResponse;
import com.mersiyanov.dmitry.newsapp.pojo.news.Pages;
import com.mersiyanov.dmitry.newsapp.ui.adapters.NewsAdapter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsFragment extends Fragment {

    private NewsAdapter adapter;
    private ApiHelper apiHelper = new ApiHelper();
    private Pages pagesCounter;
    private ProgressBar progressBar;
    private RecyclerView news_rv;
    private String query;
    private boolean loading = true;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        progressBar = rootView.findViewById(R.id.news_progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        initRecycler(rootView);
        setRetainInstance(true);
        return rootView;
    }

    public void loadNews(String query) {
        if (query != null) {
            news_rv.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            this.query = query;
            apiHelper.getApi().getNews(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<NewsResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onSuccess(NewsResponse newsResponse) {
                            adapter.setItems(newsResponse.getPosts().getNewsItem());
                            pagesCounter = newsResponse.getPages();
                            progressBar.setVisibility(View.GONE);
                            news_rv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void initRecycler(View view) {
        news_rv = view.findViewById(R.id.rv_news);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        news_rv.setLayoutManager(linearLayoutManager);
        news_rv.setHasFixedSize(true);
        adapter = new NewsAdapter();
        news_rv.setAdapter(adapter);

        addPagination(news_rv, linearLayoutManager);

    }

    private void addPagination(RecyclerView rv, final LinearLayoutManager layoutManager) {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;

                            if(pagesCounter.getNext() != null) {
                                Toast.makeText(getContext(), getString(R.string.page_loading) + pagesCounter.getNext(), Toast.LENGTH_SHORT).show();

                                apiHelper.getApi().getNewsByPage(query, pagesCounter.getNext().toString())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new SingleObserver<NewsResponse>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {}

                                            @Override
                                            public void onSuccess(NewsResponse newsResponse) {
                                                pagesCounter = newsResponse.getPages();
                                                adapter.addItems(newsResponse.getPosts().getNewsItem());
                                                loading = true;
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.e(NewsFragment.class.toString(), e.getMessage());
                                                e.printStackTrace();
                                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }

                        }
                    }
                }
            }
        });
    }

}

