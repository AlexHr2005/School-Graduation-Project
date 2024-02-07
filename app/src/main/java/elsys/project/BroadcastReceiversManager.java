package elsys.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import elsys.project.modules.phone_calls.CallReceiver;
import elsys.project.modules.sms.SmsReceiver;

public class BroadcastReceiversManager {
    private static BroadcastReceiver smsReceiver;
    private static BroadcastReceiver callReceiver;
    private static BroadcastReceiver alarmReceiver;
    private static BroadcastReceiver moduleExecutionReceiver;
    private static Context context;

    public static void registerSmsReceiver(String number, String text) {
        SmsReceiver newSmsReceiver = new SmsReceiver(number, text);
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        context.registerReceiver(newSmsReceiver, intentFilter);
        smsReceiver = newSmsReceiver;
    }

    public static void unregisterSmsReceiver() {
        context.unregisterReceiver(smsReceiver);
    }

    public static void registerCallReceiver(String number) {
        CallReceiver newCallReceiver = new CallReceiver(number);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
        context.registerReceiver(newCallReceiver, intentFilter);
        callReceiver = newCallReceiver;
    }

    public static void unregisterCallReceiver() {
        context.unregisterReceiver(callReceiver);
    }

    public static void setContext(Context context) {
        BroadcastReceiversManager.context = context;
    }
}
