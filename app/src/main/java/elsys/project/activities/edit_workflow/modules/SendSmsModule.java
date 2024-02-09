package elsys.project.activities.edit_workflow.modules;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import elsys.project.modules.sms.SendSMS;

public class SendSmsModule extends SmsModule{
    public String targetNumber;

    public SendSmsModule(String targetNumber, String text) {
        super(text);
        this.targetNumber = targetNumber;
        subhead = Module.SEND_SMS;
    }

    @Override
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionValues = super.getOptionsValues();
        optionValues.add(0, targetNumber);
        return optionValues;
    }

    @Override
    public void execute() {
        SendSMS sendSMS = new SendSMS(targetNumber);
        boolean smsSent = sendSMS.sendMessage(text);
        Log.d("lalala", smsSent + "");
        Log.d("lalala", "sms sent");
        Intent executeModule = new Intent(context, ModuleToExecute.class);
        context.sendBroadcast(executeModule);
    }

    @Override
    public void stopExecution() {

    }
}
