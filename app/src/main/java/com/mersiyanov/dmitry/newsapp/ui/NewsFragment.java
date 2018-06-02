package com.mersiyanov.dmitry.newsapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mersiyanov.dmitry.newsapp.R;
import com.mersiyanov.dmitry.newsapp.network.ApiHelper;
import com.mersiyanov.dmitry.newsapp.pojo.news.NewsItem;
import com.mersiyanov.dmitry.newsapp.pojo.news.NewsResponse;
import com.mersiyanov.dmitry.newsapp.ui.adapters.NewsAdapter;

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

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        initRecycler(rootView);

        setRetainInstance(true);

//        if(getArguments() != null) {
//            String query = getArguments().getString("query");
//            loadNews(query);
//        }


        return rootView;
    }

    public void loadNews(String query) {
        if (query != null) {
            apiHelper.getApi().getNews(query)
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
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        news_rv.setLayoutManager(linearLayoutManager);
        news_rv.setHasFixedSize(true);
        adapter = new NewsAdapter(clickListener);
        news_rv.setAdapter(adapter);


        news_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                            Toast.makeText(getContext(), "Last Item Wow !", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

            }
        });


    }

    private final NewsAdapter.OnNewsClickListener clickListener = new NewsAdapter.OnNewsClickListener() {

        @Override
        public void onClick(NewsItem item) {
            Toast.makeText(getContext(), "Клик на новость #" + item.getId(), Toast.LENGTH_SHORT).show();
        }
    };

}

