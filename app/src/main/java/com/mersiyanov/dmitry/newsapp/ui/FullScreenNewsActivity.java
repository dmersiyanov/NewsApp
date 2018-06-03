package com.mersiyanov.dmitry.newsapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mersiyanov.dmitry.newsapp.R;
import com.squareup.picasso.Picasso;

public class FullScreenNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_news);

        TextView newsDesc = findViewById(R.id.full_news_desc);
        TextView newsTitle = findViewById(R.id.full_news_title);
        ImageView newsImg = findViewById(R.id.full_news_img);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        newsDesc.setText(intent.getStringExtra("desc"));
        newsTitle.setText(intent.getStringExtra("title"));
        Picasso.get().load(intent.getStringExtra("img")).into(newsImg);
    }
}
