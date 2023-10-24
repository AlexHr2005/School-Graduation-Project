package elsys.project.modules.phone_calls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallStateReceiver extends BroadcastReceiver {
    private static StateChangeHandler stateChangeHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        Log.d("lalala", state);

        stateChangeHandler.onStateChange(state);
    }

    public static void bindHandler(StateChangeHandler handler) {
        stateChangeHandler = handler;
    }
}