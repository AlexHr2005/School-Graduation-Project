package elsys.project.modules.alarms;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import elsys.project.activities.AlarmRingActivity;
import elsys.project.App;
import elsys.project.R;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, AlarmRingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        String alarmTitle = "ALARM";

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Click here")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentIntent(pendingIntent)
                .build();

        mediaPlayer.start();

        long[] vibration_pattern = {0, 100, 1000};
        vibrator.vibrate(vibration_pattern, 0);

        // ensures that the notification can't be swiped away by the user
        startForeground(1, notification);

        return START_STICKY;
    }

    // it is called only once
    @Override
    public void onCreate() {
        super.onCreate();

        RingtoneManager ringtoneManager = new RingtoneManager(getApplicationContext());
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = ringtoneManager.getCursor();
        cursor.moveToFirst();
        String alarmUri = ringtoneManager.getRingtoneUri(cursor.getPosition()).toString();

        mediaPlayer = MediaPlayer.create(this, Uri.parse(alarmUri));
        mediaPlayer.setLooping(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    // it is called when the service is stopped
    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
