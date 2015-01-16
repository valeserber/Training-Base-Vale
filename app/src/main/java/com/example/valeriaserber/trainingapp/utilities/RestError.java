package com.example.valeriaserber.trainingapp.utilities;

import com.google.gson.annotations.SerializedName;

public class RestError {
    @SerializedName("code")
    public int code;
    @SerializedName("error")
    public String errorDetails;
}