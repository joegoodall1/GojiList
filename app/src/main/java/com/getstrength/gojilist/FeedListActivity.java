package com.getstrength.gojilist;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FeedListActivity extends Activity {

    private static final String TAG = "RecyclerViewExample";

    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();

    private RecyclerView mRecyclerView;

    private MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Allow activity to show indeterminate progressbar */
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_feeds_list);

        /* Initialize recyclerview */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*Downloading data from below url*/
        final String url = "https://api.gojimo.net/api/v4/qualifications";
        new AsyncHttpTask().execute(url);
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                /* forming th java.net.URL object */
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                /* for Get request */
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode ==  200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }

                    parseResult(response.toString());
                    result = 1; // Successful
                }else{
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {

            setProgressBarIndeterminateVisibility(false);

            /* Download complete. Lets update UI */
            if (result == 1) {
                adapter = new MyRecyclerAdapter(FeedListActivity.this, feedItemList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONArray response = new JSONArray(result);

            /*Initialize array if null*/
            if (null == feedItemList) {
                feedItemList = new ArrayList<FeedItem>();
            }


            for (int i = 0; i < response.length(); i++) {

                JSONObject qualification = response.getJSONObject(i);

                JSONArray JSONSubjects = qualification.optJSONArray("subjects");
                String[] subjects = parseSubject(JSONSubjects);

                String path = null;

                FeedItem item = new FeedItem();
                item.setTitle(qualification.optString("name"));
                item.setSubjects(subjects);

                JSONArray products = qualification.optJSONArray("default_products");
                if (products == null) {
                    continue;
                }

                for (int j = 0; j < products.length(); ++j) {

                    JSONObject product = (JSONObject) products.opt(j);

                    JSONArray assets = product.optJSONArray("assets");
                    if (assets == null) {
                        continue;
                    }

                    for (int k = 0; k < assets.length(); ++k) {

                        JSONObject asset = (JSONObject) assets.opt(k);

                        String meta = asset.optString("meta");
                        if (meta != null && meta.equals("coverImage")) {
                            path = asset.optString("path");
                            break;
                        }
                    }

                    if (path != null) {
                        break;
                    }
                }

                item.setThumbnail(path);
                feedItemList.add(item);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String[] parseSubject(JSONArray subjects) {
        if (subjects == null) {
            return null;
        }


        String[] subjectNames = new String[subjects.length()];

        for (int l = 0; l < subjects.length(); ++l) {

            JSONObject subject = subjects.optJSONObject(l);
            String title = subject.optString("title");
            subjectNames[l] = title;
        }

        return subjectNames;
    }

}

