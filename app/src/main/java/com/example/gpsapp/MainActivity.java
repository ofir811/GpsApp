package com.example.gpsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.CompoundButton;
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
    //   String phone = "0545771988";
    String phone = "0535683835";
    String phoneX;
    public BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show();

            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    if (smsMessage.getOriginatingAddress().equals(phoneX)) {
                        String messageBody = smsMessage.getMessageBody();
                        if (messageBody.contains("alarm")) {
                            startAlert();
                        }

                    }

                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aSwitch = findViewById(R.id.alarm);
        textView = findViewById(R.id.status);
        phoneText = findViewById(R.id.phone);
        setPhonex();

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        setAlarmStatus();

        registerReceiver(smsReceiver,new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    setAlarmOn();
                    Toast.makeText(getBaseContext(), "Alarm On", Toast.LENGTH_SHORT).show();
                } else {
                    setAlarmOff();
                }
            }

        });


    }


    ////// INNER METHODS /////

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

    public void saveAlarmStatus() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ALARM_SWITCH, aSwitch.isChecked()).commit();
    }

    public void setAlarmStatus() {
        aSwitch.setChecked(sharedPreferences.getBoolean(ALARM_SWITCH, false));
        setTextView(aSwitch.isChecked());
    }

    public void setTextView(boolean status) {
        if (status == true) {
            textView.setText("Alarm is on");
        } else {
            textView.setText("Alarm is off");
        }
    }

    public void startAlert() {
        Toast.makeText(this, "Alarm on", Toast.LENGTH_LONG).show();
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);

        AudioManager mobilemode = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mobilemode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        mobilemode.setStreamVolume(AudioManager.STREAM_RING,mobilemode.getStreamMaxVolume(AudioManager.STREAM_RING),0);
        r.play();
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