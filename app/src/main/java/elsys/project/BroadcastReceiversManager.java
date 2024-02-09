package elsys.project;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;

import elsys.project.activities.edit_workflow.modules.ModuleToExecute;
import elsys.project.modules.alarms.AlarmReceiver;
import elsys.project.modules.phone_calls.CallReceiver;
import elsys.project.modules.sms.SmsReceiver;

public class BroadcastReceiversManager {
    private static BroadcastReceiver smsReceiver = null;
    private static BroadcastReceiver callReceiver = null;
    private static BroadcastReceiver alarmReceiver = null;
    private static BroadcastReceiver moduleExecutionReceiver = null;
    private static Context context;

    public static void registerSmsReceiver(String number, String text) {
        SmsReceiver newSmsReceiver = new SmsReceiver(number, text);
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        context.registerReceiver(newSmsReceiver, intentFilter);
        smsReceiver = newSmsReceiver;
    }

    public static void unregisterSmsReceiver() {
        context.unregisterReceiver(smsReceiver);
        smsReceiver = null;
    }

    public static void registerCallReceiver(String number) {
        CallReceiver newCallReceiver = new CallReceiver(number);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
        context.registerReceiver(newCallReceiver, intentFilter);
        callReceiver = newCallReceiver;
    }

    public static void unregisterCallReceiver() {
        context.unregisterReceiver(callReceiver);
        callReceiver = null;
    }

    public static void registerAlarmReceiver() {
        AlarmReceiver newAlarmReceiver = new AlarmReceiver();
        context.registerReceiver(newAlarmReceiver, new IntentFilter("com.example.myapp.CUSTOM_ACTION"));
        alarmReceiver = newAlarmReceiver;
    }

    public static void unRegisterAlarmReceiver() {
        context.unregisterReceiver(alarmReceiver);
        alarmReceiver = null;
    }

    public static void registerModuleExecutionReceiver() {
        ModuleToExecute moduleToExecute = new ModuleToExecute();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("project.elsys.STOP_WORKFLOW");
        intentFilter.addAction("project.elsys.EXECUTE_MODULE");
        context.registerReceiver(moduleToExecute, intentFilter);
        moduleExecutionReceiver = moduleToExecute;
    }

    public static void unregisterModuleExecutionReceiver() {
        context.unregisterReceiver(moduleExecutionReceiver);
        moduleExecutionReceiver = null;
    }

    public static void setContext(Context context) {
        BroadcastReceiversManager.context = context;
    }
}
