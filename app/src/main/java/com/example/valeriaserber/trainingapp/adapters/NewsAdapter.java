package com.example.valeriaserber.trainingapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.valeriaserber.trainingapp.R;
import com.example.valeriaserber.trainingapp.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News>{

    private final Activity context;
    private final List<News> newsList;

    private ImageView mPicture;
    private TextView mTitle;
    private TextView mDescription;

    public NewsAdapter(Activity context, List<News> newsList) {
        super(context, R.layout.list_view_news, newsList);
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_view_news, null, false);
        setUi();
        init(position);
        return rowView;
    }

    private void setUi() {
        mPicture = (ImageView) context.findViewById(R.id.list_view_news_image_view);
        mTitle = (TextView) context.findViewById(R.id.list_view_news_title_text_view);
        mDescription = (TextView) context.findViewById(R.id.list_view_news_description_text_view);
    }

    private void init(int position) {
        Picasso.with(context)
                .load(newsList.get(position).getPicture())
                .into(mPicture);
        mTitle.setText(newsList.get(position).getTitle());
        mDescription.setText(newsList.get(position).getText());
    }
}