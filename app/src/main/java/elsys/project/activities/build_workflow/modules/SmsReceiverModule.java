package elsys.project.activities.build_workflow.modules;

import android.util.Log;

import java.util.ArrayList;

import elsys.project.BroadcastReceiversManager;

public class SmsReceiverModule extends SmsModule{
    public String senderNumber;
    public static final String[] PERMISSIONS_NEEDED = {
        "android.permission.RECEIVE_SMS",
        "android.permission.READ_SMS"
    };

    public SmsReceiverModule(String senderNumber, String text) {
        super(text);
        this.senderNumber = senderNumber;
        subhead = Module.RECEIVE_SMS;
    }

    @Override
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionValues = super.getOptionsValues();
        optionValues.add(0, senderNumber);
        return optionValues;
    }

    @Override
    public void execute() {
        BroadcastReceiversManager.registerSmsReceiver(senderNumber, text);
    }

    @Override
    public void stopExecution() {
        BroadcastReceiversManager.unregisterSmsReceiver();
        Log.d("lalala", "sms receiver module stopped");
    }
}