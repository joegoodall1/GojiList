package com.getstrength.gojilist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

public class SubjectActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "qualification_name";
    public static final String SUBJECT_NAME = "subject_name";
    public static final String BACKDROP_PATH = "backdrop_path";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        Intent intent = getIntent();
        final String qualificationName = intent.getStringExtra(EXTRA_NAME);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loadBackdrop(intent.getStringExtra(BACKDROP_PATH));
        Bundle b = intent.getExtras();
        String[] array = b.getStringArray(SUBJECT_NAME);
        if (array != null) {
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(adapter);
        }


    }

    private void loadBackdrop(String path) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(path)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }


}
