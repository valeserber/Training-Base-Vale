package com.example.valeriaserber.trainingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.valeriaserber.trainingapp.R;
import com.example.valeriaserber.trainingapp.TrainingApplication;
import com.example.valeriaserber.trainingapp.adapters.NewsAdapter;
import com.example.valeriaserber.trainingapp.model.News;
import com.example.valeriaserber.trainingapp.model.NewsObject;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewsFragment extends Fragment {

    private List<News> mNewsList;
    private ListView mNewsListView;

    public static NewsFragment newInstance() {
        NewsFragment f = new NewsFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        setUi(rootView);
        getNews();
        return rootView;
    }

    private void setUi(View rootView) {
        mNewsListView = (ListView) rootView.findViewById(R.id.fragment_news_list_view);
    }

    private void getNews() {
        TrainingApplication.sNewsService.getNews( new Callback<NewsObject>() {
            @Override
            public void success(NewsObject newses, Response response) {
                mNewsList = newses.getResults();
                if (!mNewsList.isEmpty()) {
                    NewsAdapter adapter = new NewsAdapter(getActivity().getApplicationContext(), mNewsList);
                    mNewsListView.setAdapter(adapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
//                Log.v("lala", error.getMessage());
            }
        });
    }
}