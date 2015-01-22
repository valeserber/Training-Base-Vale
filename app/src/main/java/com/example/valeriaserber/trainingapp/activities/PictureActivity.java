package com.example.valeriaserber.trainingapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.valeriaserber.trainingapp.R;
import com.squareup.picasso.Picasso;

public class PictureActivity extends Activity {

    public static final String PICTURE_SOURCE = "PICTURE_SOURCE";

    private ImageView mPicture;
    private String mPictureSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturefull);
        setUi();
        init();
    }

    private void setUi() {
        mPicture = (ImageView) findViewById(R.id.activity_picturefull_image_view);
    }

    private void init() {
        Intent intent = getIntent();
        mPictureSource = intent.getStringExtra(PICTURE_SOURCE);
        Picasso.with(getApplicationContext())
                .load(mPictureSource)
                .into(mPicture);
    }
}