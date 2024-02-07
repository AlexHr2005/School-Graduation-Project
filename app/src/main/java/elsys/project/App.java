package elsys.project;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import elsys.project.R;
import elsys.project.WorkflowsList;
import elsys.project.activities.edit_workflow.modules.Module;

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

        WorkflowsList.setWorkflowsDir(workflowsDir);
        WorkflowsList.loadWorkflows(this);

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
