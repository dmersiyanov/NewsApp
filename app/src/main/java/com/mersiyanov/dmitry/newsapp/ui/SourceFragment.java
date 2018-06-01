package com.mersiyanov.dmitry.newsapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mersiyanov.dmitry.newsapp.R;
import com.mersiyanov.dmitry.newsapp.network.ApiHelper;
import com.mersiyanov.dmitry.newsapp.pojo.SourceItem;
import com.mersiyanov.dmitry.newsapp.pojo.SourcesResponse;
import com.mersiyanov.dmitry.newsapp.ui.adapters.SourcesAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SourceFragment extends Fragment {

    private RecyclerView sources_rv;
    private SourcesAdapter adapter;
    private List<SourceItem> items = new ArrayList<>();
    private ApiHelper apiHelper = new ApiHelper();

    public static SourceFragment newInstance() {
        SourceFragment fragment = new SourceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sources, container, false);

        initRecycler(rootView);

        loadFeeds("дом");

        return rootView;
    }

    private void initRecycler(View view) {
        sources_rv = view.findViewById(R.id.rv_sources);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        sources_rv.setLayoutManager(layoutManager);
        sources_rv.setHasFixedSize(true);
        adapter = new SourcesAdapter(clickListener);
        sources_rv.setAdapter(adapter);
    }

    public void loadFeeds(String query) {
        if(query != null) {
            apiHelper.getApi().getSources(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<SourcesResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) { }

                        @Override
                        public void onSuccess(SourcesResponse sourcesResponse) {
                            items = sourcesResponse.getFeeds();
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

    SourcesAdapter.OnSourceClickListener clickListener = new SourcesAdapter.OnSourceClickListener() {
        @Override
        public void onClick(SourceItem item) {
            Toast.makeText(getContext(), "Вы подписаны на " + item.getTitle(), Toast.LENGTH_SHORT).show();

        }
    };

}
