package elsys.project.modules.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import elsys.project.activities.build_workflow.modules.Module;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmType = intent.getStringExtra("Alarm type");
        if(alarmType.equals(Module.SILENT_ALARM)) {
            Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
            executeModule.putExtra("command", "unregister alarmReceiver");
            context.sendBroadcast(executeModule);
        }
        else if(alarmType.equals(Module.SOUND_ALARM)) {
            startAlarmService(context);
        }
    }

    private void startAlarmService(Context context) {
        Intent intentService = new Intent(context, AlarmService.class);
        // in the newer version of Android (API >= 26) it is recommended starting services as foreground,
        // because these are more immune to restriction by the OS
        context.startForegroundService(intentService);
    }
}