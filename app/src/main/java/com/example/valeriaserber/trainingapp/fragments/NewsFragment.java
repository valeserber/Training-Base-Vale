package com.example.valeriaserber.trainingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button mRefreshButton;
    private ProgressBar mRefreshProgressBar;
    private View mEmptyView;
    private View mErrorView;

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
        setListeners();
        getNews();
        return rootView;
    }

    private void setUi(View rootView) {
        mEmptyView = rootView.findViewById(R.id.fragment_news_empty_container);
        mErrorView = rootView.findViewById(R.id.fragment_news_error_container);
        mNewsListView = (ListView) rootView.findViewById(R.id.fragment_news_list_view);
        mRefreshButton = (Button) rootView.findViewById(R.id.fragment_news_refresh_button);
        mRefreshProgressBar = (ProgressBar) rootView.findViewById(R.id.fragment_news_refresh_progress_bar);
    }

    private void setListeners() {
//        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.v("lala", "list view clicked "+String.valueOf(position));
//            }
//        });
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefreshButton.setVisibility(View.GONE);
                mRefreshProgressBar.setVisibility(View.VISIBLE);
                getNews();
            }
        });
    }

    private void getNews() {
        TrainingApplication.sNewsService.getNews( new Callback<NewsObject>() {
            @Override
            public void success(NewsObject newses, Response response) {
                mErrorView.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.GONE);
                mRefreshProgressBar.setVisibility(View.GONE);
                mRefreshButton.setVisibility(View.VISIBLE);
                mNewsList = newses.getResults();
                if (!mNewsList.isEmpty()) {
                    NewsAdapter adapter = new NewsAdapter(getActivity().getApplicationContext(), mNewsList);
                    mNewsListView.setAdapter(adapter);
                    mNewsListView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mRefreshButton.setVisibility(View.VISIBLE);
                mRefreshProgressBar.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.GONE);
                mErrorView.setVisibility(View.VISIBLE);
                mNewsListView.setVisibility(View.GONE);
            }
        });
    }
}