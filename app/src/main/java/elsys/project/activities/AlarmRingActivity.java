package elsys.project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import elsys.project.R;
import elsys.project.activities.build_workflow.modules.Module;
import elsys.project.modules.alarms.Alarm;
import elsys.project.modules.alarms.AlarmService;

public class AlarmRingActivity extends AppCompatActivity {
    private static Alarm snoozeAlarm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ring);
    }

    public void dismiss(View view) {
        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);
        Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
        executeModule.putExtra("command", "unregister alarmReceiver");
        getApplicationContext().sendBroadcast(executeModule);
        snoozeAlarm = null;
        finish();
    }

    public void snooze(View view) {
        EditText snoozeTime = findViewById(R.id.snoozeTime);
        int minutesToSnooze = Integer.parseInt(snoozeTime.getText().toString());

        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);

        snoozeAlarm = new Alarm(
                0,
                minutesToSnooze
        );

        snoozeAlarm.schedule(this, Module.SOUND_ALARM);

        finish();
    }

    public static void cancelSnoozedAlarm() {
        if(snoozeAlarm != null) {
            snoozeAlarm.cancel();
        }
    }
}