package com.rishi.family;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class GiftRecActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_rec);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebView mywebview = (WebView) findViewById(R.id.web);
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        mywebview.loadUrl("https://www.uncommongoods.com/sunny/giftfinder/intro");
    }
}
