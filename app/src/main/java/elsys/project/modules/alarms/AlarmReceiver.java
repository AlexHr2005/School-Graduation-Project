package elsys.project.modules.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import elsys.project.activities.edit_workflow.modules.Module;
import elsys.project.activities.edit_workflow.modules.ModuleToExecute;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "It's time!", Toast.LENGTH_SHORT).show();
        String alarmType = intent.getStringExtra("Alarm type");
        Log.d("lalala", alarmType);
        if(alarmType.equals(Module.BACKGROUND_ALARM)) {
            Log.d("lalala", "Background alarm ringed!");
            Intent executeModule = new Intent(context, ModuleToExecute.class);
            context.sendBroadcast(executeModule);
        }
        else if(alarmType.equals(Module.SOUND_ALARM)) {
            startAlarmService(context);
        }
    }

    private void startAlarmService(Context context) {
        Intent intentService = new Intent(context, AlarmService.class);
        //in the newer version of Android it is recommended starting services as foreground,
        //because these are more immune to restriction by the OS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        }
        else context.startService(intentService);
    }
}