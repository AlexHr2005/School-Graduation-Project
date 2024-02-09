package elsys.project.activities.edit_workflow.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import elsys.project.BroadcastReceiversManager;

public class ModuleToExecute extends BroadcastReceiver {
    private static ArrayList<Module> modules;
    private static int moduleToExecute;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("project.elsys.EXECUTE_MODULE")) {
            String command = intent.getStringExtra("command");
            if(command != null) {
                if(command.equals("unregister smsReceiver")) {
                    BroadcastReceiversManager.unregisterSmsReceiver();
                }
                else if(command.equals("unregister callReceiver")) {
                    BroadcastReceiversManager.unregisterCallReceiver();
                }
                else if(command.equals("unregister alarmReceiver")) {
                    BroadcastReceiversManager.unRegisterAlarmReceiver();
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
        }
    }

    public static void setModules(ArrayList<Module> modules) {
        ModuleToExecute.modules = modules;
        moduleToExecute = -1;
    }
}