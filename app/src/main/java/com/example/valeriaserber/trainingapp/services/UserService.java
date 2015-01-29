package com.example.valeriaserber.trainingapp.services;

import com.example.valeriaserber.trainingapp.model.User;
import com.example.valeriaserber.trainingapp.model.SignUpObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Body;

public interface UserService {

    @GET("/1/login")
    public void logIn(@Query("username") String email, @Query("password") String password, Callback<User> cb);

    @POST("/1/users")
    public void signUp(@Body SignUpObject user, Callback<User> cb);

    @GET("/1/users/{id}")
    public void getUser(@Path("id") String objectId, Callback<User> cb);
}