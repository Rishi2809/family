package com.rishi.family.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.rishi.family.R;
import com.rishi.family.activity.sms.SmsSchedulerActivity;

public class AutoSmsScheduler extends AppCompatActivity {

    private static Context context;
    public CardView cardSmsScheduler;

    public static Context getAppContext() {
        return AutoSmsScheduler.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_sms_scheduler);

        cardSmsScheduler = findViewById(R.id.cardSmsScheduler);
        cardSmsScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AutoSmsScheduler.this, SmsSchedulerActivity.class));
            }
        });

        AutoSmsScheduler.context = getApplicationContext();
    }

}
