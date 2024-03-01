package elsys.project.activities.build_workflow.modules;

import android.content.Context;

import java.util.ArrayList;

public abstract class Module {
    public String title;
    public String subhead;
    protected static Context context;

    // titles
    public static final String ALARM = "ALARM";
    public static final String SMS = "SMS";
    public static final String PHONE_CALL = "PHONE CALL";
    // subheads
    public static final String SILENT_ALARM = "silent";
    public static final String SOUND_ALARM = "sound";
    public static final String SEND_SMS = "send";
    public static final String RECEIVE_SMS = "receive";
    public static final String RECEIVE_PHONE_CALL = "receive";

    // returns the values of module’s options(parameters)
    public abstract ArrayList<String> getOptionsValues();

    public abstract void execute();
    public abstract void stopExecution();

    public static void setContext(Context context) {
        Module.context = context;
    }
}
