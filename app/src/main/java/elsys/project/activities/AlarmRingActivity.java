package elsys.project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import elsys.project.R;
import elsys.project.activities.edit_workflow.modules.Module;
import elsys.project.activities.edit_workflow.modules.ModuleToExecute;
import elsys.project.modules.alarms.Alarm;
import elsys.project.modules.alarms.AlarmService;

public class AlarmRingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ring);
    }

    public void dismissAlarm(View view) {
        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);
        Intent executeModule = new Intent(getApplicationContext(), ModuleToExecute.class);
        getApplicationContext().sendBroadcast(executeModule);
        finish();
    }

    public void snoozeAlarm(View view) {
        EditText snoozeTime = findViewById(R.id.snoozeTime);
        int minutesToSnooze = Integer.parseInt(snoozeTime.getText().toString());

        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);

        Alarm alarm = new Alarm(
                0,
                minutesToSnooze
        );

        alarm.schedule(this, Module.SOUND_ALARM);

        finish();
    }
}