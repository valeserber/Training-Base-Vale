package com.example.valeriaserber.trainingapp.services;

import android.content.Context;

import com.example.valeriaserber.trainingapp.R;

import retrofit.RequestInterceptor;

public class ParseRequestInterceptor implements RequestInterceptor{

    private static final String APPLICATION_ID = "X-Parse-Application-Id";
    private static final String REST_API_KEY = "X-Parse-REST-API-Key";
    private static final String CONTENT_TYPE = "Content-Type";

    private final Context context;

    public ParseRequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader(APPLICATION_ID, context.getString(R.string.applicationId));
        request.addHeader(REST_API_KEY, context.getString(R.string.restApiKey));
        request.addHeader(CONTENT_TYPE, context.getString(R.string.contentType));
    }
}