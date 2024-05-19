package elsys.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import elsys.project.activities.build_workflow.modules.ModuleExecutionReceiver;
import elsys.project.modules.alarms.AlarmReceiver;
import elsys.project.modules.phone_calls.CallReceiver;
import elsys.project.modules.sms.SmsReceiver;

public class BroadcastReceiversManager {
    private static BroadcastReceiver smsReceiver = null;
    private static BroadcastReceiver callReceiver = null;
    private static BroadcastReceiver alarmReceiver = null;
    // it is registered in the start of the workflow’s execution
    // and is called when a new module must be executed
    private static BroadcastReceiver moduleExecutionReceiver = null;
    private static Context context;

    public static void registerSmsReceiver(String number, String text) {
        smsReceiver = new SmsReceiver(number, text);
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        context.registerReceiver(smsReceiver, intentFilter);
    }

    public static void unregisterSmsReceiver() {
        context.unregisterReceiver(smsReceiver);
        smsReceiver = null;
    }

    public static void registerCallReceiver(String number) {
        callReceiver = new CallReceiver(number);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
        context.registerReceiver(callReceiver, intentFilter);
    }

    public static void unregisterCallReceiver() {
        context.unregisterReceiver(callReceiver);
        callReceiver = null;
    }

    public static void registerAlarmReceiver() {
        alarmReceiver = new AlarmReceiver();
        context.registerReceiver(alarmReceiver, new IntentFilter("com.example.myapp.FIRE_ALARM"));
    }

    public static void unregisterAlarmReceiver() {
        context.unregisterReceiver(alarmReceiver);
        alarmReceiver = null;
    }

    public static void registerModuleExecutionReceiver() {
        ModuleExecutionReceiver newModuleExecutionReceiver = new ModuleExecutionReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("project.elsys.STOP_WORKFLOW");
        intentFilter.addAction("project.elsys.EXECUTE_MODULE");
        context.registerReceiver(newModuleExecutionReceiver, intentFilter);
        moduleExecutionReceiver = newModuleExecutionReceiver;
    }

    public static void unregisterModuleExecutionReceiver() {
        context.unregisterReceiver(moduleExecutionReceiver);
        moduleExecutionReceiver = null;
    }

    public static void setContext(Context context) {
        BroadcastReceiversManager.context = context;
    }
}
