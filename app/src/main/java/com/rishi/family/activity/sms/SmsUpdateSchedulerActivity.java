package com.rishi.family.activity.sms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rishi.family.MyBroadcastReceiver;
import com.rishi.family.R;
import com.rishi.family.dbhelper.SmsDatabaseHelper;
import com.rishi.family.model.Sms;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class SmsUpdateSchedulerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final int PERMISSIONS_REQUEST_SEND_SMS = 101;
    Button btnUpdateSchedule;
    TextView textViewDate;
    TextView textViewTime;
    RelativeLayout relativeLayoutSelectTime;
    RelativeLayout relativeLayoutSelectDate;
    EditText editTextMessage;
    EditText editTextToRecipient;
    Calendar calendar;
    int mHour, mMinute, mYear, mMonth, mDay;
    SmsDatabaseHelper databaseHelper;
    Sms sms = new Sms();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_update_scheduler);
        calendar = Calendar.getInstance();
        databaseHelper = new SmsDatabaseHelper(this);
        btnUpdateSchedule = findViewById(R.id.btnUpdateSchedule);
        textViewDate= findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
        relativeLayoutSelectTime = findViewById(R.id.relativeLayoutSelectTime);
        relativeLayoutSelectDate = findViewById(R.id.relativeLayoutSelectDate);
        editTextMessage = findViewById(R.id.editTextMessage);
        editTextToRecipient= findViewById(R.id.editTextToRecipient);
        sms = (Sms) getIntent().getSerializableExtra("sms");
        Toast.makeText(this, sms.getNumber(), Toast.LENGTH_LONG).show();
        calendar.setTimeInMillis(sms.getMilli());

        String selectedDate = sms.getDate();

        SpannableString ss1 = new SpannableString(selectedDate);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 2, 0);
        textViewDate.setText(ss1);

        String selectedTime = sms.getTime();

        SpannableString ss2 = new SpannableString(selectedTime);
        ss2.setSpan(new RelativeSizeSpan(1.5f), 0, 5, 0);
        textViewTime.setText(ss2);

        editTextMessage.setText(sms.getMessage());

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sms.getMilli());

        editTextToRecipient.setText(sms.getNumber());
    }


    public void updateSchedule(View view) {
        if (editTextToRecipient.getText().toString().length() == 0
                || textViewDate.getText().toString().length() == 0
                || textViewTime.getText().toString().length() == 0
                || editTextMessage.getText().toString().length() == 0) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            // delete previous alarm
            cancelPreviousAlarm();
            String contactNumber = editTextToRecipient.getText().toString();

            String message = editTextMessage.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("number", contactNumber);
            bundle.putString("message", message);

            Intent intent = new Intent(this, MyBroadcastReceiver.class);
            intent.putExtras(bundle);
            String actionUri = "com.scheduler.action.SMS_SEND";
            intent.setAction(actionUri);
            int _id = (int) System.currentTimeMillis();

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, _id, intent, 0);
            calendar.set(mYear, mMonth, mDay, mHour, mMinute);

            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, "Schedule updated", Toast.LENGTH_LONG).show();

            if (databaseHelper.addSms(_id, contactNumber, message, textViewTime.getText().toString(),
                    textViewDate.getText().toString(), (int) calendar.getTimeInMillis())) {
                Toast.makeText(this, "updated to db", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SmsSchedulerActivity.class));
            } else {
                Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cancelPreviousAlarm() {
        String dpid = sms.getId();
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        String strAction = "com.scheduler.action.SMS_SEND";
        intent.setAction(strAction);
        int pid = Integer.parseInt(dpid);
        databaseHelper.deleteById(dpid);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, pid, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public void getDate(View view) {
        // initialize
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, mYear, mMonth, mDay);
        datePickerDialog.show(getFragmentManager(), "datePickerDialog");
    }

    public void getTime(View view) {
        // initialize
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, mHour, mMinute, DateFormat.is24HourFormat(this));
        timePickerDialog.show(getFragmentManager(), "TimePickerDialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;
        calendar.add(Calendar.DATE, 0);

        final String[] MONTHS = {"Jan", "Feb", "Mar",
                "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};
        String mon = MONTHS[monthOfYear];

        String selectedDate = dayOfMonth + "  " + mon
                + " " + year;

        SpannableString ss1 = new SpannableString(selectedDate);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0, 2, 0); // set
        textViewDate.setText(ss1);

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        mHour = hourOfDay;
        mMinute = minute;

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR, hourOfDay);

        calendar.set(Calendar.AM_PM, Calendar.AM);

        String str;
        if (calendar.get(Calendar.AM_PM) == Calendar.AM)
            str = "AM";
        else
            str = "PM";

        String hours;
        if (calendar.get(Calendar.HOUR) > 9) {
            hours = String.valueOf(calendar.get(Calendar.HOUR));
        } else {
            hours = "0" + String.valueOf(calendar.get(Calendar.HOUR));
        }

        String minutes;
        if (minute > 9) {
            minutes = String.valueOf(minute);
        } else {
            minutes = "0" + String.valueOf(minute);
        }
        if (hours.equalsIgnoreCase("00")) {
            hours = "12";
        }

        String selectedTime = hours + ":" + minutes
                + "  " + str;

        SpannableString ss2 = new SpannableString(selectedTime);
        ss2.setSpan(new RelativeSizeSpan(1.5f), 0, 5, 0);

        textViewTime.setText(ss2);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR, hourOfDay);
    }
}
