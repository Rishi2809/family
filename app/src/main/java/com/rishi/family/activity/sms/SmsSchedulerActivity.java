package com.rishi.family.activity.sms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rishi.family.R;
import com.rishi.family.adapter.SmsArrayAdapter;
import com.rishi.family.dbhelper.SmsDatabaseHelper;
import com.rishi.family.model.Sms;

import java.util.ArrayList;
import java.util.List;

public class SmsSchedulerActivity extends AppCompatActivity {

    public ListView smsListView;
    FloatingActionButton fab;
    TextView textViewNoSchedule;
    SmsDatabaseHelper databaseHelper;
    int position = 1;
    private List<Sms> smsArrayList = new ArrayList<Sms>();
    private SmsArrayAdapter smsArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_scheduler);
        smsListView = findViewById(R.id.smsListView);
        textViewNoSchedule = findViewById(R.id.textViewNoSchedule);


        databaseHelper = new SmsDatabaseHelper(this);
        registerForContextMenu(smsListView);
        fetchSms();
    }

    private void fetchSms() {
        smsArrayList = databaseHelper.getAll();
        if (smsArrayList.size() > 0) {
            smsListView.setVisibility(View.VISIBLE);
            textViewNoSchedule.setVisibility(View.GONE);
            Log.d("SMSARRAY", smsArrayList.get(0).toString());
        }
        smsArrayAdapter = new SmsArrayAdapter(this, smsArrayList);
        smsListView.setAdapter(smsArrayAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        MenuInflater inflater = getMenuInflater();
        menu.setHeaderTitle("Select what you wanna do");
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                Intent intent = new Intent(this, SmsUpdateSchedulerActivity.class);
                intent.putExtra("sms", smsArrayList.get(position));
                startActivity(intent);
                return true;
            case R.id.menu_delete:
                deleteSchedule();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteSchedule() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure?");
        dialog.setTitle("Note delete");
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.setCancelable(false);

        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (databaseHelper.deleteById(smsArrayList.get(position).getId())) {
                    Toast.makeText(getApplicationContext(), "SMS Schedule Deleted", Toast.LENGTH_SHORT).show();
                    smsArrayList.remove(position);
                    smsArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        dialog.show();
    }

    public void fab(View view) {
        startActivity(new Intent(this, CreateSmsScheduleActivity.class));
    }
}
