package com.example.valeriaserber.trainingapp.services;

import com.example.valeriaserber.trainingapp.model.NewsObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface NewsService {

    @GET("/1/classes/news")
    public void getNews(Callback<NewsObject> cb);
}
