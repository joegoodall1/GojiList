package com.getstrength.gojilist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class SubjectActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "qualification_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        Intent intent = getIntent();
        final String qualificationName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(qualificationName);

        loadBackdrop();
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load("https://gojimo.s3.amazonaws.com/production/assets/c0b03fd3-2de5-42c7-905f-097d7c18a677/640x960_ASVAB_Splash.png")
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }


}
