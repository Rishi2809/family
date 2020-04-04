package com.rishi.family;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreenActivity extends AppCompatActivity {
    String uid;
    FirebaseAuth firebaseAuth;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        uid = getIntent().getStringExtra("uid");
        viewPager = findViewById(R.id.viewPager1);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(HomeScreenActivity.this);
        viewPager.setAdapter(viewPageAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menuLogout:
                logout();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void profile(View view) {
        Toast.makeText(this, "Profile Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void logout() {
        firebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void addevents(View view) {
        Intent intent = new Intent(this, InsertDataActivity.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void viewevents(View view) {
        Intent intent = new Intent(this, DatesActivity.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }

    public void familytree(View view) {
        //      Toast.makeText(this, "Family Tree Coming Soon", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FamilyTreeActivity.class);
        startActivity(intent);
    }

    public void gifts(View view) {
        //Toast.makeText(this, "Gift Recommendation Coming Soon", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, GiftRecActivity.class);
        startActivity(intent);
    }

}
