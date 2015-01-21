package com.example.valeriaserber.trainingapp;

import android.app.Application;
import android.content.Context;

import com.example.valeriaserber.trainingapp.services.ParseRequestInterceptor;
import com.example.valeriaserber.trainingapp.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;

public class TrainingApplication extends Application {

    private static final String ENDPOINT = "https://api.parse.com";
    private static final String RETROFIT = "RETROFIT";

    public static UserService sUserService;

    private static Gson sGson;
    private static RequestInterceptor sParseRequestInterceptor;
    private static Context sContext;

    static {
        buildRestServices();
    }

    public static void buildRestServices() {
        TrainingApplication.sParseRequestInterceptor = new ParseRequestInterceptor(getAppContext());
        sGson = new GsonBuilder().create();
        setupServices();
    }

    public static void setupServices() {
        RestAdapter trainingAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setConverter(new GsonConverter(sGson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog(RETROFIT))
                .setRequestInterceptor(sParseRequestInterceptor)
                .build();
        sUserService = trainingAdapter.create(UserService.class);
    }

    public static Context getAppContext() {
        return TrainingApplication.sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TrainingApplication.sContext = getApplicationContext();
    }
}