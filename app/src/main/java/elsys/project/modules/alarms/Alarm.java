package elsys.project.modules.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import elsys.project.R;

public class Alarm {
    private int hour, minute;
    public static int requestCode = 0;
    private PendingIntent alarmPendingIntent;

    public Alarm(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public void schedule(Context context, String alarmType) {
        requestCode++;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent("com.example.myapp.FIRE_ALARM");
        intent.putExtra("Alarm type", alarmType);

        alarmPendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);

        //RTC_WAKEUP - this type of alarm will wake the device if it's screen is off
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if(alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmPendingIntent);
            }
        }
        else alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmPendingIntent);
    }

    public void cancel() {
        alarmPendingIntent.cancel();
    }
}
