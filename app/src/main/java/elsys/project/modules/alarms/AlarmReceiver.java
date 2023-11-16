package elsys.project.modules.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "It's time!", Toast.LENGTH_SHORT).show();
        //startAlarmService(context, intent);
    }

    private void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        //in the newer version of Android it is recommended starting services as foreground,
        //because these are more immune to restriction by the OS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        }
        else context.startService(intentService);
    }
}