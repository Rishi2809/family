package com.rishi.family;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatesActivity extends AppCompatActivity {
    ShimmerRecyclerView shimmerRecycler;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    List<Dates> mlist;
    MyListAdapter adapter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        shimmerRecycler = (ShimmerRecyclerView) findViewById(R.id.shimmer_recycler_view);
        shimmerRecycler.showShimmerAdapter();
        shimmerRecycler.setHasFixedSize(true);
        shimmerRecycler.setLayoutManager(new LinearLayoutManager(this));
        mlist = new ArrayList<>();
        uid = getIntent().getStringExtra("uid");
        getData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                adapter = new MyListAdapter(mlist);
                shimmerRecycler.setAdapter(adapter);
                shimmerRecycler.hideShimmerAdapter();
            }
        }, 4000);


    }

    public void getData() {
        reference = database.getReference("Users/" + uid + "/events/");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        String name = npsnapshot.child("name").getValue().toString();
                        String dob = npsnapshot.child("dob").getValue().toString();
                        String oca = npsnapshot.child("occassion").getValue().toString();
                        String remindme = npsnapshot.child("relation").getValue().toString();

                        Dates dates = new Dates();
                        dates.setName(name);
                        dates.setDob(dob);
                        dates.setOccassion(oca);
                        dates.setRemindme(remindme);
                        mlist.add(dates);
                        Log.d("hello", "added");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
