package elsys.project.activities.build_workflow.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import elsys.project.BroadcastReceiversManager;

public class ModuleExecutionReceiver extends BroadcastReceiver {
    private static ArrayList<Module> modules;
    private static int moduleToExecute;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("project.elsys.EXECUTE_MODULE")) {
            String extra = intent.getStringExtra("command");
            if(extra != null) {
                if(extra.equals("unregister smsReceiver")) {
                    BroadcastReceiversManager.unregisterSmsReceiver();
                }
                else if(extra.equals("unregister callReceiver")) {
                    BroadcastReceiversManager.unregisterCallReceiver();
                }
                else if(extra.equals("unregister alarmReceiver")) {
                    BroadcastReceiversManager.unregisterAlarmReceiver();
                }
            }
            moduleToExecute++;
            Log.d("lalala", "" + moduleToExecute);
            if(moduleToExecute < modules.size()) {
                String a = modules.get(moduleToExecute).title + " " + modules.get(moduleToExecute).subhead;
                Log.d("lalala", a);
                modules.get(moduleToExecute).execute();
            }
            else {
                Log.d("lalala", "end of workflow");
                moduleToExecute = -1;
                Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
                context.sendBroadcast(executeModule);
            }
        }
        else if(intent.getAction().equals("project.elsys.STOP_WORKFLOW")) {
            Log.d("lalala", "STOP WORKFLOW");
            modules.get(moduleToExecute).stopExecution();
            BroadcastReceiversManager.unregisterModuleExecutionReceiver();
            Log.d("lalala", "WORKFLOW STOPPED");
        }
    }

    public static void setModules(ArrayList<Module> modules) {
        ModuleExecutionReceiver.modules = modules;
        moduleToExecute = -1;
    }
}