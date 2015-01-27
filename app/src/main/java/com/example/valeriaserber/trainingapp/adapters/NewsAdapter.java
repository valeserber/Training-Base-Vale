package com.example.valeriaserber.trainingapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.valeriaserber.trainingapp.R;
import com.example.valeriaserber.trainingapp.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News>{

    private final Context context;
    private final List<News> mNewsList;

    private ImageView mPicture;
    private TextView mTitle;
    private TextView mDescription;
    private Button mLikeButton;

    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
        this.context = context;
        this.mNewsList = newsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        News news = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_view_news, parent, false);
        }
        setUi(convertView);
        setListeners();
        init(news);
        return convertView;
    }

    private void setUi(View rootView) {
        mPicture = (ImageView) rootView.findViewById(R.id.list_view_news_image_view);
        mTitle = (TextView) rootView.findViewById(R.id.list_view_news_title_text_view);
        mDescription = (TextView) rootView.findViewById(R.id.list_view_news_description_text_view);
        mLikeButton = (Button) rootView.findViewById(R.id.list_view_news_like_button);
        mLikeButton.setSelected(false);
    }

    private void setListeners() {
        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLikeButton.isPressed()) {
                    mLikeButton.setPressed(false);
                    mLikeButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like_off, 0, 0);
                } else{
                    mLikeButton.setPressed(true);
                    mLikeButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.like_on, 0, 0);
                }
            }
        });
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
}