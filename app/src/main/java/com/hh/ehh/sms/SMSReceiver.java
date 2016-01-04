package com.hh.ehh.sms;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.hh.ehh.LoginActivity;
import com.hh.ehh.R;

public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS = "SMS";

    public void onReceive(Context context, Intent intent) {
        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages;

        if (myBundle != null) {
            try {
                Object[] pdus = (Object[]) myBundle.get("pdus");
                messages = new SmsMessage[pdus.length];

                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    if (messages[i].getDisplayOriginatingAddress().equals(context.getResources().getString(R.string.phone_number))) {
                        Intent smsIntent = new Intent(context, LoginActivity.class);
                        smsIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        smsIntent.putExtra(SMS, messages[i].getMessageBody());
                        context.startActivity(smsIntent);
}
                }
            } catch (NullPointerException e) {
                Log.wtf("IDIOTS", "You have stupidly passed a null pointer ... what a shame!");
            }
//                strMessage += "SMS From: " + messages[i].getOriginatingAddress();
//                strMessage += " : ";
//                strMessage += messages[i].getMessageBody();
//                strMessage += "\n";
        }
    }
}

