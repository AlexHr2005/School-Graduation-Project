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
            if(moduleToExecute < modules.size()) {
                String a = modules.get(moduleToExecute).title + " " + modules.get(moduleToExecute).subhead;
                modules.get(moduleToExecute).execute();
                Log.d("lalala", "Module No" + (moduleToExecute + 1) + " has started executing");
            }
            else {
                moduleToExecute = -1;
                Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
                context.sendBroadcast(executeModule);
            }
        }
        else if(intent.getAction().equals("project.elsys.STOP_WORKFLOW")) {
            modules.get(moduleToExecute).stopExecution();
            BroadcastReceiversManager.unregisterModuleExecutionReceiver();
        }
    }

    public static void setModules(ArrayList<Module> modules) {
        ModuleExecutionReceiver.modules = modules;
        moduleToExecute = -1;
    }
}