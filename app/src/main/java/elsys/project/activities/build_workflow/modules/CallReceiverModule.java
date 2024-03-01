package elsys.project.activities.build_workflow.modules;

import android.util.Log;

import java.util.ArrayList;

import elsys.project.BroadcastReceiversManager;

public class CallReceiverModule extends PhoneCallModule{
    private String callerNumber;

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
        Log.d("lalala", "call receiver module stopped");
    }

    @Override
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionValues = super.getOptionsValues();
        optionValues.add(callerNumber);
        return optionValues;
    }
}
