package elsys.project.activities.edit_workflow.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Arrays;

public class ModuleToExecute extends BroadcastReceiver {
    private static ArrayList<Module> modules;
    private static int moduleToExecute = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        modules.get(moduleToExecute).execute();
        moduleToExecute++;
    }

    public static void setModules(ArrayList<Module> modules) {
        ModuleToExecute.modules = modules;
        moduleToExecute = 0;
    }
}