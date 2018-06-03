package com.mersiyanov.dmitry.newsapp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mersiyanov.dmitry.newsapp.NewsApp;
import com.mersiyanov.dmitry.newsapp.R;
import com.mersiyanov.dmitry.newsapp.ui.news.NewsFragment;
import com.mersiyanov.dmitry.newsapp.ui.news.NewsPresenter;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private NewsFragment newsFragment;
    private SourceFragment sourceFragment;
//    private ApiHelper apiHelper = new ApiHelper();
//    private NewsRepository repository = new NewsRepository(apiHelper);
    @Inject NewsPresenter newsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsApp.component.injectsAct(this);


        initUI();

        newsFragment = NewsFragment.newInstance();
        sourceFragment = SourceFragment.newInstance();

    }

    private void initUI() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout =  findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) myActionMenuItem.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
//                newsFragment.loadNews(query);
                newsPresenter.load(query);
                sourceFragment.loadFeeds(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return newsFragment;
                case 1: return sourceFragment;
                default: return null;
            }

        }

        @Override
        public int getCount() { return 2; }


    }
}
