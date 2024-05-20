package elsys.project.activities.build_workflow.modules;

import android.util.Log;

import java.util.ArrayList;

import elsys.project.BroadcastReceiversManager;

public class CallReceiverModule extends PhoneCallModule{
    private String callerNumber;
    public static final String[] PERMISSIONS_NEEDED = {
            "android.permission.READ_PHONE_STATE",
            "android.permission.READ_CALL_LOG"
    };

    public CallReceiverModule(String callerNumber) {
        super();
        this.callerNumber = callerNumber;
        this.subhead = Module.RECEIVE_PHONE_CALL;
    }

    @Override
    public void execute() {
        BroadcastReceiversManager.registerCallReceiver(callerNumber);
    }

    @Override
    public void stopExecution() {
        BroadcastReceiversManager.unregisterCallReceiver();
    }

    @Override
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionValues = super.getOptionsValues();
        optionValues.add(callerNumber);
        return optionValues;
    }
}
