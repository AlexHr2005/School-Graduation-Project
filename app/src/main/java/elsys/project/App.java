package elsys.project;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.io.File;

import elsys.project.activities.build_workflow.modules.Module;

public class App extends Application {
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

        File filesDir = getFilesDir();
        File workflowsDir = new File(filesDir, getString(R.string.workflows_dir));

        if(!workflowsDir.exists()) {
            workflowsDir.mkdirs();
        }

        WorkflowsList.setWorkflowsDir(workflowsDir);
        WorkflowsList.loadWorkflowsToList(this);
        WorkflowsList.setContext(this);

        Module.setContext(getApplicationContext());
        BroadcastReceiversManager.setContext(getApplicationContext());
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
