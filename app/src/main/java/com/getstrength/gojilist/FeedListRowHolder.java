package com.getstrength.gojilist;

/**
 * Created by Joe on 03/02/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FeedListRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected ImageView thumbnail;
    protected TextView title;
    private Context context;

    public FeedListRowHolder(Context context, View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        this.title = (TextView) view.findViewById(R.id.title);
        // Store the context
        this.context = context;
        // Attach a click listener to the entire row view
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getLayoutPosition(); // gets item position
        //User user = user.get(position);
        // We can access the data within the views
        Toast.makeText(context, title.getText(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, SubjectActivity.class);
        context.startActivity(intent);
    }


}
