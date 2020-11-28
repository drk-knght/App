package com.example.androidapplication;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.logging.Level;

public class AlarmReceiver1 extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String text = bundle.getString("event");
        String date = bundle.getString("date") + " on " + bundle.getString("time");
        String email1=bundle.getString("email");
        String message="Email notification from Application android\n"+text+" has been scheduled by you\n for "+date;
        SendMail sm = new SendMail(context, email1, text, message);
        sm.execute();
    }


}