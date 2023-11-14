package elsys.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import elsys.project.modules.alarms.Alarm;

public class AlarmActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private Button setAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        setAlarmButton = findViewById(R.id.setAlarmButton);
        timePicker = findViewById(R.id.timePicker);
    }

    public void setAlarm(View v) {
        Alarm alarm = new Alarm(timePicker.getHour(), timePicker.getMinute());
        alarm.schedule(this);
    }
}