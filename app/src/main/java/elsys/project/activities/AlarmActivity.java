package elsys.project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import elsys.project.R;
import elsys.project.modules.alarms.Alarm;

public class AlarmActivity extends AppCompatActivity {
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        timePicker = findViewById(R.id.timePicker);
    }

    public void setAlarm(View v) {
        Alarm alarm = new Alarm(timePicker.getHour(), timePicker.getMinute());
        //alarm.schedule(this);
    }
}