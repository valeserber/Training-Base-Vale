package com.example.valeriaserber.trainingapp.services;

import com.example.valeriaserber.trainingapp.model.SessionObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface UserService {

    @GET("/1/login")
    public void logIn(@Query("username") String email, @Query("password") String password, Callback<SessionObject> cb);
}