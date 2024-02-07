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
        String command = intent.getStringExtra("command");
        if(command != null) {
            if(command.equals("unregister smsReceiver")) {
                BroadcastReceiversManager.unregisterSmsReceiver();
            }
            else if(command.equals("unregister callReceiver")) {
                BroadcastReceiversManager.unregisterCallReceiver();
            }
        }
        moduleToExecute++;
        Log.d("lalala", "" + moduleToExecute);
        if(moduleToExecute < modules.size()) {
            //TODO: think of a way to minimize or avoid using recursion
            String a = modules.get(moduleToExecute).title + " " + modules.get(moduleToExecute).subhead;
            Log.d("lalala", a);
            modules.get(moduleToExecute).execute();
        }
        else {
            Log.d("lalala", "end of workflow");
        }
    }

    public static void setModules(ArrayList<Module> modules) {
        ModuleToExecute.modules = modules;
        moduleToExecute = -1;
    }
}