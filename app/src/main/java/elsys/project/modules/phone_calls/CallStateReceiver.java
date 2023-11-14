package elsys.project.modules.phone_calls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallStateReceiver extends BroadcastReceiver {
    private static NewCallHandler stateChangeHandler;
    //variable that says whether new missed or rejected call exists
    private static boolean newCallExists = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            newCallExists = true;
        }
        if(TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            newCallExists = false;
        }
        if(TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            if(newCallExists == true) {
                stateChangeHandler.callHandler();
                newCallExists = false;
            }
        }
    }

    public static void bindHandler(NewCallHandler handler) {
        stateChangeHandler = handler;
    }
}