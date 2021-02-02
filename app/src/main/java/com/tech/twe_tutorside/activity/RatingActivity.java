package com.tech.twe_tutorside.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tech.twe_tutorside.R;


public class RatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
    }

    public void backToSucess(View view) {
        startActivity(new Intent(RatingActivity.this,HomeActvity.class));
        finish();
    }
}