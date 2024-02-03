package elsys.project.modules.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import elsys.project.R;

public class Alarm {
    private int hour, minute;
    public static int requestCode = 0;

    public Alarm(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public void schedule(Context context, String alarmType) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.d("lalala", alarmType + " written in the Alarm class");
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("Alarm type", alarmType);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);

        Log.d("lalala", "alarm created and ready to be scheduled for after " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        //RTC_WAKEUP - this type of alarm will wake the device if it's screen is off
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmPendingIntent);
    }
}
