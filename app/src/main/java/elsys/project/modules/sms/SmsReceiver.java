package elsys.project.modules.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    public static final String pdus = "pdus";
    private String number;
    private String text;

    public SmsReceiver(String number, String text) {
        this.number = number;
        this.text = text;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("lalala", number + " " + text);
        Bundle bundle = intent.getExtras();
        String format = bundle.getString("format");

        // a message might be divided into multiple PDUs
        Object[] pdus = (Object[]) bundle.get(SmsReceiver.pdus);
        Log.d("lalala", bundle.get(SmsReceiver.pdus).getClass().toString());
        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0], format);
        String sender = smsMessage.getOriginatingAddress();

        String messageBody = "";
        for(Object pdu : pdus) {
            SmsMessage currSmsMessage = SmsMessage.createFromPdu(
                    (byte[]) pdu, format);
            messageBody += currSmsMessage.getMessageBody();
        }
        Log.d("lalala", pdus.length + " pdus");

        Log.d("lalala", sender + ": " + messageBody);

        boolean rightSMS = true;

        if(!number.equals("")) {
            if(!number.equals(sender)) {
                rightSMS = false;
            }
        }

        if(!text.equals("")) {
            if(!text.equals(messageBody)) {
                rightSMS = false;
            }
        }

        if(rightSMS) {
            Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
            Log.d("lalala", "1");
            executeModule.putExtra("command", "unregister smsReceiver");
            Log.d("lalala", "2");
            context.sendBroadcast(executeModule);
            Log.d("lalala", "3");
        }
    }
}