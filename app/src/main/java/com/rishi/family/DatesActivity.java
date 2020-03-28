package com.rishi.family;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatesActivity extends AppCompatActivity {
    ShimmerRecyclerView shimmerRecycler;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    ArrayList<Dates> mlist, nlist;
    TextView tv;
    MyListAdapter adapter;
    String uid;
    FirebaseAuth firebaseAuth;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        shimmerRecycler = findViewById(R.id.shimmer_recycler_view);
        shimmerRecycler.showShimmerAdapter();
        shimmerRecycler.setHasFixedSize(true);
        shimmerRecycler.setLayoutManager(new LinearLayoutManager(this));
        tv = findViewById(R.id.txtView);
        mlist = new ArrayList<>();
        nlist = new ArrayList<>();
        uid = getIntent().getStringExtra("uid");
        if (uid == "") {
            uid = firebaseAuth.getInstance().getUid();
        }
        getData();
        //  mlist = db.getAllContacts();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                adapter = new MyListAdapter(nlist);
                shimmerRecycler.setAdapter(adapter);
                shimmerRecycler.hideShimmerAdapter();
            }
        }, 8000);

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
                        dates.setDob(CalendarUtils.stringToCalendar(CalendarUtils.formatDate(dob)));
                        dates.setOccassion(oca);
                        dates.setRemindme(remindme);
                        mlist.add(dates);
                        Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                        Log.d("hello", "added");
                    }
                }

                SortListService.sortList(getApplicationContext(), mlist, new SortListService.SortCallback() {

                    @Override
                    public void onSort(ArrayList<Dates> date) {
//                        tv.append(Html.fromHtml("<b>Name\t\tDate\t\t\tEvent\n</b><br>"));
                        Log.d("data", "here");

                        for (Dates dates : date) {
                            Log.d("data", dates.getName() + "" + dates.getSBDay() + "i" + dates.getOccassion());
                            nlist.add(dates);
                 //           tv.append("\n" + dates.getName() + "\t\t" + Html.fromHtml(dates.getSBDay()) + "\t\t" + dates.getOccassion());
                        }


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}