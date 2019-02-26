package com.example.gpsapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                Toast.makeText(context, "scanning", Toast.LENGTH_SHORT).show();
                if (smsMessage.getOriginatingAddress().equals("+972535683835")) {
                    String messageBody = smsMessage.getMessageBody();
                    Toast.makeText(context, messageBody, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


}
