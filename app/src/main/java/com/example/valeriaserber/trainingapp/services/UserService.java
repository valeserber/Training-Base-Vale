package com.example.valeriaserber.trainingapp.services;

import com.example.valeriaserber.trainingapp.model.SessionObject;
import com.example.valeriaserber.trainingapp.model.SignUpObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.Body;
import retrofit.http.POST;

public interface UserService {

    @GET("/1/login")
    public void logIn(@Query("username") String email, @Query("password") String password, Callback<SessionObject> cb);

    @POST("/1/users")
    public void signUp(@Body SignUpObject user, Callback<SessionObject> cb);
}