package com.kazuaiko.pokegousan2;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by naitou_ka on 2016/07/29.
 */
public class MyAdapter extends ArrayAdapter<FeedItem> {
    public MyAdapter(Context context, List<FeedItem> objects){
        super(context,R.layout.list_item_feed,R.id.title,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        //View view = convertView;

        TextView title = (TextView) view.findViewById(R.id.title);

        //TextView description = (TextView) view.findViewById(R.id.description);
        //TextView category = (TextView) view.findViewById(R.id.title);

        //TextView date = (TextView) view.findViewById(R.id.date);
        //TextView pubDate = (TextView) view.findViewById(R.id.date);

        FeedItem item = getItem(position);

        title.setText(item.getTitle());
        //description.setText(item.getDescription());
        //category.setText(item.getCategory());
        //date.setText(item.getDate());
        //pubDate.setText(item.getPubDate());

        /*
        title.setText(item.getTitle());
        description.setText(item.getDescription());
        category.setText(item.getCategory());
        date.setText(item.getDate());
        */

        /*
        if(position%3==0){
            title.setBackgroundColor(Color.parseColor("#ffcccc"));
        }else if(position%3==1){
            title.setBackgroundColor(Color.parseColor("#99ccff"));
        }else{
            title.setBackgroundColor(Color.parseColor("#ccffcc"));
        }
        */
        if(position%2==0){
            title.setBackgroundColor(Color.parseColor("#ffffff"));
            title.setHeight(130);
        }else{
            //title.setBackgroundColor(Color.parseColor("#99ccff"));
            title.setBackgroundColor(Color.parseColor("#ffcccc"));
            title.setHeight(130);
        }

        /*
        if(position%2==0){
            title.setBackgroundColor(Color.parseColor("#ffffff"));
        }else{
            title.setBackgroundColor(Color.parseColor("#ffff99"));
        }
        */

        /*
        if(position%3==0){
            title.setBackgroundColor(Color.parseColor("#ffffff"));
        }else if(position%3==1){
            title.setBackgroundColor(Color.parseColor("#ffcccc"));
        }else{
            title.setBackgroundColor(Color.parseColor("#ff99cc"));
        }
        */

        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.item_motion);
        view.startAnimation(anim);

        return view;
    }
}
