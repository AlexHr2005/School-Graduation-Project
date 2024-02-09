package elsys.project.modules.phone_calls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import elsys.project.activities.edit_workflow.modules.ModuleToExecute;

public class CallReceiver extends BroadcastReceiver {
    //variable that says whether the user has a unanswered call
    private boolean unansweredCall = false;
    private String number;

    public CallReceiver(String number) {
        this.number = number;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("lalala", "a");
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            unansweredCall = true;
            Log.d("lalala", "b");
        }
        //case for covering the user answering a call
        if(TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            unansweredCall = false;
        }
        //when there are no more calls in progress, checking if the user has answered the call
        if(TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            Log.d("lalala", "c");
            if(unansweredCall) {
                Log.d("lalala", "d");
                unansweredCall = false;
                Log.d("lalala", "d1");
                if(CallRecordRetriever.returnLastCall(context).equals(number)) {
                    Log.d("lalala", "e");
                    Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
                    executeModule.putExtra("command", "unregister callReceiver");
                    context.sendBroadcast(executeModule);
                }
            }
        }
    }
}