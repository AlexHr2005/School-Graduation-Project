package elsys.project.activities.edit_workflow.modules;

import android.content.Context;
import android.content.IntentFilter;

import java.util.ArrayList;

import elsys.project.BroadcastReceiversManager;
import elsys.project.modules.sms.SmsReceiver;

public class SmsReceiverModule extends SmsModule{
    public String senderNumber;
    public Context context;

    public SmsReceiverModule(String senderNumber, String text, Context context) {
        super(text);
        this.senderNumber = senderNumber;
        subhead = Module.RECEIVE_SMS;
        this.context = context;
    }

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
}