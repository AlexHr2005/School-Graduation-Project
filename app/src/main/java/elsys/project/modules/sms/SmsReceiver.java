package elsys.project.modules.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import elsys.project.activities.edit_workflow.modules.ModuleToExecute;

public class SmsReceiver extends BroadcastReceiver {

    public static final String pdu_type = "pdus";
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

        Object[] pdus = (Object[]) bundle.get(pdu_type);
        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0], format);
        Log.d("lalala", pdus.length + " pdus");

        String sender = smsMessage.getDisplayOriginatingAddress();
        String messageBody = smsMessage.getMessageBody();

        Log.d("lalala", sender + ": " + messageBody);

        if(sender.equals(number) && messageBody.equals(text)) {
            Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
            Log.d("lalala", "1");
            executeModule.putExtra("command", "unregister smsReceiver");
            Log.d("lalala", "2");
            context.sendBroadcast(executeModule);
            Log.d("lalala", "3");
        }
    }
}