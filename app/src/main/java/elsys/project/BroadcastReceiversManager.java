package elsys.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import elsys.project.modules.sms.SmsReceiver;

public class BroadcastReceiversManager {
    private static BroadcastReceiver smsReceiver;
    private static BroadcastReceiver callReceiver;
    private static BroadcastReceiver alarmReceiver;
    private static BroadcastReceiver moduleExecutionReceiver;
    private static Context context;

    public static void registerSmsReceiver() {
        SmsReceiver newSmsReceiver = new SmsReceiver();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        context.registerReceiver(newSmsReceiver, intentFilter);
        smsReceiver = newSmsReceiver;
    }

    public static void unregisterSmsReceiver() {
        context.unregisterReceiver(smsReceiver);
    }

    public static void setContext(Context context) {
        BroadcastReceiversManager.context = context;
    }
}
