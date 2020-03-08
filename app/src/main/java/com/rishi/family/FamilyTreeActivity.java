package com.rishi.family;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class FamilyTreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_tree);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
