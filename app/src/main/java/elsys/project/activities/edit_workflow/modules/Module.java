package elsys.project.activities.edit_workflow.modules;

import android.content.Context;

import java.util.ArrayList;

import elsys.project.R;

public abstract class Module {
    //Title can be: ALARM, PHONE CALL, SMS
    public String title;
    public String subhead;
    protected static Context context;
    public static final String ALARM = "ALARM";
    public static final String BACKGROUND_ALARM = "background";
    public static final String SOUND_ALARM = "sound";
    public static final String SMS = "SMS";
    public static final String SEND_SMS = "send";
    public static final String RECEIVE_SMS = "receive";
    public static final String PHONE_CALL = "PHONE CALL";
    public static final String RECEIVE_PHONE_CALL = "receive";

    public abstract ArrayList<String> getOptionsValues();

    public abstract void execute();
    public abstract void stopExecution();

    public static void setContext(Context context) {
        Module.context = context;
    }
}
