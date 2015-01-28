package com.example.valeriaserber.trainingapp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

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

    public static final int ITEMS_PER_PAGE = 1;

    private List<News> mNewsList;
    private ListView mNewsListView;
    private Button mRefreshButton;
    private ProgressBar mRefreshProgressBar;
    private View mEmptyView;
    private View mErrorView;
    private View mFooterView;
    private SwipeRefreshLayout mSwipeView;
    private NewsAdapter mAdapter;
    private int itemsShown = 0;

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
        mFooterView = inflater.inflate(R.layout.list_view_footer, null, false);
        setUi(rootView);
        setListeners();
        loadNews();
        return rootView;
    }

    private void setUi(View rootView) {
        mEmptyView = rootView.findViewById(R.id.fragment_news_empty_container);
        mErrorView = rootView.findViewById(R.id.fragment_news_error_container);
        mNewsListView = (ListView) rootView.findViewById(R.id.fragment_news_list_view);
        mRefreshButton = (Button) rootView.findViewById(R.id.fragment_news_refresh_button);
        mRefreshProgressBar = (ProgressBar) rootView.findViewById(R.id.fragment_news_refresh_progress_bar);
        mSwipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.fragment_news_swipe);
    }

    private void setListeners() {
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefreshButton.setVisibility(View.GONE);
                mRefreshProgressBar.setVisibility(View.VISIBLE);
                loadNews();
            }
        });
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        mNewsListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int currentVisibleItemCount;
            private int currentScrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentVisibleItemCount = visibleItemCount;
                if (firstVisibleItem == 0) {
                    mSwipeView.setEnabled(true);
                } else {
                    mSwipeView.setEnabled(false);
                }
            }

            private void isScrollCompleted() {
                if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
                    addFooter();
                    loadNews();
                }
            }
        });
    }

    private void refreshContent() {
        itemsShown = 0;
        mAdapter = null;
        mSwipeView.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNews();
                mSwipeView.setRefreshing(false);
            }
        }, 2000);
    }

    private void loadNews() {
        TrainingApplication.sNewsService.getNews(ITEMS_PER_PAGE, itemsShown, new Callback<NewsObject>() {
            @Override
            public void success(NewsObject newsObject, Response response) {
                resetViews();
                mNewsList = newsObject.getResults();
                if (!mNewsList.isEmpty()) {
                    if (mAdapter == null) {
                        mAdapter = new NewsAdapter(getActivity().getApplicationContext(), mNewsList);
                    } else {
                        mAdapter.update(mNewsList);
                    }
                    itemsShown += mNewsList.size();
                    mNewsListView.setAdapter(mAdapter);
                    mNewsListView.setVisibility(View.VISIBLE);
                } else if (itemsShown == 0) {
                    mNewsListView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                }
                removeFooter();
            }

            @Override
            public void failure(RetrofitError error) {
                removeFooter();
                loadErrorViews();
            }
        });
    }

    private void addFooter() {
        mNewsListView.addFooterView(mFooterView);
    }

    private void removeFooter() {
        mNewsListView.removeFooterView(mFooterView);
    }

    private void loadErrorViews() {
        mRefreshButton.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.VISIBLE);
        mRefreshProgressBar.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mNewsListView.setVisibility(View.GONE);
    }

    private void resetViews() {
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mRefreshProgressBar.setVisibility(View.GONE);
        mRefreshButton.setVisibility(View.VISIBLE);
    }
}