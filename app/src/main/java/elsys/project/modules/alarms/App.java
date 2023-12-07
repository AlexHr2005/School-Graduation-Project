package elsys.project.modules.alarms;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import elsys.project.R;
import elsys.project.WorkflowsList;

public class App extends Application {
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

        File filesDir = getFilesDir();
        File workflowsDir = new File(filesDir, String.valueOf(R.string.workflows_dir));

        if(!workflowsDir.exists()) {
            workflowsDir.mkdirs();
        }

        /*File file1 = new File(workflowsDir, "my first workflow");
        File file2 = new File(workflowsDir, "i am lazy");
        File file3 = new File(workflowsDir, "ffff");

        try {
            file1.createNewFile();
            file2.createNewFile();
            file3.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        WorkflowsList.setWorkflowsDir(workflowsDir);
        WorkflowsList.loadWorkflows(this);
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID,
                "Alarm Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
