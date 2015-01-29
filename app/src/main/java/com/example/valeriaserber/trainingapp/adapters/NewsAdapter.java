package com.example.valeriaserber.trainingapp.adapters;

import android.content.Context;
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

    private final Context context;
    private ImageView mPicture;
    private TextView mTitle;
    private TextView mDescription;

    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        News news = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_view_news, parent, false);
        }
        setUi(convertView);
        init(news);
        return convertView;
    }

    private void setUi(View rootView) {
        mPicture = (ImageView) rootView.findViewById(R.id.list_view_news_image_view);
        mTitle = (TextView) rootView.findViewById(R.id.list_view_news_title_text_view);
        mDescription = (TextView) rootView.findViewById(R.id.list_view_news_description_text_view);
    }

    private void init(News news) {
        mTitle.setText(news.getTitle());
        mDescription.setText(news.getText());
        Picasso.with(context)
                .load(news.getPicture())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.news_image_view_empty)
                .into(mPicture);
    }

    public void update(List<News> newsList) {
        for (News n : newsList) {
            this.add(n);
        }
        notifyDataSetChanged();
    }
}