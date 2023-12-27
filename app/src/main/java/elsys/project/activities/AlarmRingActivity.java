package elsys.project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import elsys.project.R;
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
        finish();
    }

    public void snoozeAlarm(View view) {
        EditText snoozeTime = findViewById(R.id.snoozeTime);
        int minutesToSnooze = Integer.parseInt(snoozeTime.getText().toString());

        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);

        Calendar calendar = Calendar.getInstance();

        Alarm alarm = new Alarm(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE) + minutesToSnooze
        );

        alarm.schedule(this);

        finish();
    }
}