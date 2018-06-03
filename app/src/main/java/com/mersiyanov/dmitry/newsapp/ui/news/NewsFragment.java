package com.mersiyanov.dmitry.newsapp.ui.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mersiyanov.dmitry.newsapp.NewsApp;
import com.mersiyanov.dmitry.newsapp.R;
import com.mersiyanov.dmitry.newsapp.network.ApiHelper;
import com.mersiyanov.dmitry.newsapp.pojo.news.NewsItem;
import com.mersiyanov.dmitry.newsapp.pojo.news.NewsResponse;
import com.mersiyanov.dmitry.newsapp.pojo.news.Pages;
import com.mersiyanov.dmitry.newsapp.ui.FullScreenNewsActivity;
import com.mersiyanov.dmitry.newsapp.ui.adapters.NewsAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsFragment extends Fragment implements NewsScreenContract.View {

    @Inject
    NewsPresenter presenter;
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
    public void onAttach(Context context) {
        NewsApp.component.injectsFrag(this);

        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        progressBar = rootView.findViewById(R.id.news_progress);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        initRecycler(rootView);

        presenter.attachView(this);

        return rootView;
    }

//    public void loadNews(String query) {
//        if (query != null) {
//            news_rv.setVisibility(View.GONE);
//            progressBar.setVisibility(View.VISIBLE);
//            this.query = query;
//            apiHelper.getApi().getNews(query)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new SingleObserver<NewsResponse>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                        }
//
//                        @Override
//                        public void onSuccess(NewsResponse newsResponse) {
//                            adapter.setItems(newsResponse.getPosts().getNewsItem());
//                            pagesCounter = newsResponse.getPages();
//                            progressBar.setVisibility(View.GONE);
//                            news_rv.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            e.printStackTrace();
//                            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//    }

    private void initRecycler(View view) {
        news_rv = view.findViewById(R.id.rv_news);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        news_rv.setLayoutManager(linearLayoutManager);
        news_rv.setHasFixedSize(true);
        adapter = new NewsAdapter(clickListener);
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
                                Toast.makeText(getContext(), "Загрузка страницы #" +pagesCounter.getNext(), Toast.LENGTH_SHORT).show();

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
//                                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }

                        }
                    }
                }
            }
        });
    }

    private final NewsAdapter.OnNewsClickListener clickListener = new NewsAdapter.OnNewsClickListener() {

        @Override
        public void onClick(NewsItem item) {
            Intent intent = new Intent(getContext(), FullScreenNewsActivity.class);
            intent.putExtra("desc", item.getContent());
            intent.putExtra("img", item.getImg());
            intent.putExtra("title", item.getTitle());
            startActivity(intent);

//            Toast.makeText(getContext(), "Клик на новость #" + item.getId(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void showLoading() {
        news_rv.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        Snackbar.make(getView(), "Произошла ошибка", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showData(List<NewsItem> newsItems) {
        adapter.setItems(newsItems);
//        pagesCounter = newsResponse.getPages();
        progressBar.setVisibility(View.GONE);
        news_rv.setVisibility(View.VISIBLE);
    }
}

