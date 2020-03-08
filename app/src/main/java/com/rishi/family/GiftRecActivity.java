package com.rishi.family;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GiftRecActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_rec);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
