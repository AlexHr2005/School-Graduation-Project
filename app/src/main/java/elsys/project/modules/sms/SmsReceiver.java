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
    private static SmsRetriever smsRetriever;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages;
        String format = bundle.getString("format");

        Object[] pdus = (Object[]) bundle.get(pdu_type);
        messages = new SmsMessage[pdus.length];

        for(int i = 0; i < pdus.length; i++) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                messages[i] = SmsMessage.createFromPdu((byte[])pdus[i], format);
            }
            else {
                messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            }
            String message = "SMS from " + messages[i].getOriginatingAddress();
            message += ": " + messages[i].getMessageBody();
            Log.d("lalala", message);
        }
        Intent executeModule = new Intent(context, ModuleToExecute.class);
        Log.d("lalala", "1");
        executeModule.putExtra("command", "unregister smsReceiver");
        Log.d("lalala", "2");
        context.sendBroadcast(executeModule);
        Log.d("lalala", "3");
    }

    public static void bindRetriever(SmsRetriever retriever) {
        smsRetriever = retriever;
    }
}