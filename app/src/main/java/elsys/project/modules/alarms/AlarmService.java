package elsys.project.modules.alarms;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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

        Log.d("lalala", "bababa");

        String alarmTitle = "Boyko is talking";

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Bla bla bla")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentIntent(pendingIntent)
                .build();

        mediaPlayer.start();

        Log.d("lalala", "boyko started talking");

        long[] vibration_pattern = {0, 100, 1000};
        vibrator.vibrate(vibration_pattern, 0);

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.boyko);
        mediaPlayer.setLooping(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

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
