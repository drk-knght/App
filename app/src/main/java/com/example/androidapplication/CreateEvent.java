package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidapplication.Member;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CreateEvent extends AppCompatActivity implements View.OnClickListener {
    Button btn_time, btn_date, btn_done;
    EditText editText_task;
    String timeToNotify;
    DatabaseReference reference;
    Member member;
    int h, mi, d, mo, y;
    String event1, date1, time1;
    int key, key3;
    String key1, key2;
    String currentuser;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        btn_date = findViewById(R.id.btn_date);
        btn_time = findViewById(R.id.btn_time);
        btn_done = findViewById(R.id.btn_done);
        editText_task = (EditText) findViewById(R.id.editText_task);
        btn_date.setOnClickListener(this);
        btn_time.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        key = new Random().nextInt();
        key1 = Integer.toString(key);

        reference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_date) {
            selectDate();
        } else if (v == btn_time) {
            selectTime();
        } else if (v == btn_done) {
            submit();
        }
    }

    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int Minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeToNotify = hourOfDay + ":" + minute;
                btn_time.setText(FormatTime(hourOfDay, minute));
                h = hourOfDay;
                mi = minute;
            }
        }, hour, Minute, false);
        timePickerDialog.show();
    }

    private void selectDate() {
        Calendar calendar1 = Calendar.getInstance();
        int Year = calendar1.get(Calendar.YEAR);
        int Month = calendar1.get(Calendar.MONTH);
        int day = calendar1.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btn_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                y = year;
                mo = month;
                d = dayOfMonth;
            }
        }, Year, Month, day);
        datePickerDialog.show();
    }

    private void submit() {
        event1 = (editText_task.getText().toString());
        date1 = (btn_time.getText().toString());
        time1 = (btn_date.getText().toString());
        if ((editText_task.getText().toString().trim()).isEmpty()) {
            Toast.makeText(this, "Please enter Task", Toast.LENGTH_SHORT).show();
        } else if (((btn_time.getText().toString()).equals("Select Time")))
            Toast.makeText(this, "Please enter Time", Toast.LENGTH_SHORT).show();
        else if (((btn_date.getText().toString()).equals("Select Date")))
            Toast.makeText(this, "Please enter Date", Toast.LENGTH_SHORT).show();
        else {
            key2 = (Long.toString(System.currentTimeMillis()));
            member = new Member();
            member.setDate(btn_date.getText().toString().trim());
            member.setTime(btn_time.getText().toString().trim());
            member.setTask(editText_task.getText().toString().trim());
            member.setKey(key1);
            member.setKey2(key2);
            currentuser = fAuth.getInstance().getCurrentUser().getUid();

            reference.child("To-Do").child(currentuser).child("Task" + key2).setValue(member);
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show();
            setAlarm();
            finish();
        }

    }

    public String FormatTime(int hour1, int minute1) {
        String time = "";
        String formattedMinute;
        if (minute1 / 10 == 0)
            formattedMinute = "0" + minute1;
        else
            formattedMinute = "" + minute1;
        if (hour1 == 0)
            time = "12" + ":" + formattedMinute + "AM";
        if (hour1 < 10)
            time = "0" + hour1 + ":" + formattedMinute + "AM";
        if ((hour1 < 12) && (hour1 >= 10))
            time = hour1 + ":" + formattedMinute + "AM";
        if (hour1 == 12)
            time = "12" + ":" + formattedMinute + "PM";
        if ((hour1 > 12) && (hour1 < 22))
            time = "0" + (hour1 - 12) + ":" + formattedMinute + "PM";
        if (hour1 >= 22)
            time = hour1 + ":" + formattedMinute + "PM";
        return (time);
    }

    private void setAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("event", event1);
        notificationIntent.putExtra("time", time1);
        notificationIntent.putExtra("date", date1);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, key, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.YEAR, y);
        cal.set(Calendar.MONTH, mo);
        cal.set(Calendar.DAY_OF_MONTH, d);
        cal.set(Calendar.HOUR_OF_DAY, h);
        cal.set(Calendar.MINUTE, mi);
        cal.set(Calendar.SECOND, 0);


        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
    }
}