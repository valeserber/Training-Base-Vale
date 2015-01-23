package com.example.valeriaserber.trainingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.valeriaserber.trainingapp.R;
import com.example.valeriaserber.trainingapp.TrainingApplication;
import com.example.valeriaserber.trainingapp.adapters.NewsAdapter;
import com.example.valeriaserber.trainingapp.model.News;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewsFragment extends Fragment {

    private List<News> mNewsList;
    private ListView mNewsListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        setUi(rootView);
//        getNews();
        List<News> news = new ArrayList<News>();
        news.add(new News(1, "ash ketchup", 1,"peppepe", "http://bulbapedia.bulbagarden.net/wiki/File:Ash_XY.png"));
        news.add(new News(2, "ash ketchup", 1,"peppepe", "http://bulbapedia.bulbagarden.net/wiki/File:Ash_XY.png"));
        news.add(new News(3, "ash ketchup", 1,"peppepe", "http://bulbapedia.bulbagarden.net/wiki/File:Ash_XY.png"));
        NewsAdapter adapter = new NewsAdapter(getActivity(), news);
        mNewsListView.setAdapter(adapter);
        return rootView;
    }

    private void setUi(View rootView) {
        mNewsListView = (ListView) rootView.findViewById(R.id.fragment_news_list_view);
    }

    private void getNews() {
        TrainingApplication.sUserService.getNews(new Callback<List<News>>() {
            @Override
            public void success(List<News> newsList, Response response) {
//                for(News n: newsList){
//                    Log.v("lala", n.getTitle());
//                }
//                NewsAdapter adapter = new NewsAdapter(getActivity(), news);
//                mNewsListView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}