package com.example.gpsapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private SmsManager smsManager = SmsManager.getDefault();
    private Switch aSwitch;
    private TextView textView;
    private TextView phoneText;
    static final String SHARED_PREFS = "sharedPrefs";
    static final String ALARM_SWITCH = "alarmSwitch";
    SharedPreferences sharedPreferences;

 //   String phone = "0535683835";
 //   String phone = "0523490177";
    String phone = "0535683835";
    String phoneX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aSwitch = findViewById(R.id.alarm);
        textView = findViewById(R.id.status);
        phoneText = findViewById(R.id.phone);
        setPhonex();

        sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        setAlarmStatus();

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    setAlarmOn();
                    Toast.makeText(getBaseContext(),"Alarm On",Toast.LENGTH_SHORT).show();
                } else {
                    setAlarmOff();
                }
            }

        });


    }



    ////// INNER METHODS /////

    public void setAlarmStatusView (View view) {
        setAlarmStatus();
    }




    public void setAlarmOn() {
        smsManager.sendTextMessage(phone, null, "shock123456", null, null);
        setTextView(true);
        saveAlarmStatus();

    }
    public void setAlarmOff() {
        smsManager.sendTextMessage(phone, null, "noshock123456", null, null);
        setTextView(false);
        saveAlarmStatus();
    }

    public void setPhonex() {
        phoneX = "+972" + phone.substring(1);
        phoneText.setText("Phone: " + phone);
    }

    public void saveAlarmStatus(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ALARM_SWITCH,aSwitch.isChecked()).commit();
    }

    public void setAlarmStatus() {
        aSwitch.setChecked(sharedPreferences.getBoolean(ALARM_SWITCH,false));
        setTextView(aSwitch.isChecked());
    }

    public void setTextView(boolean status) {
        if (status == true) {
            textView.setText("Alarm is on");
        }
        else {
            textView.setText("Alarm is off");
        }
    }


}
 /*
     public void setAlarmStatus(){

        String status = new String();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri,new String[]{"_id","address","date","body"},null,null,null);
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            if(cursor.getString(1).equals(phoneX)) { //if sender is gps
                String body = cursor.getString(3);
                if (body.contains("ok")) {
                    status = body;
                    break;
                }
            }
        }
        if (status.contains("noshock")) {
            aSwitch.setChecked(false);
            textView.setText("Alarm is off");
        } else {
            aSwitch.setChecked(true);
            textView.setText("Alarm is on");
        }

        Toast.makeText(getBaseContext(),"Alarm status updated",Toast.LENGTH_SHORT).show();

    }

  */