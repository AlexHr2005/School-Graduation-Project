package elsys.project.activities.build_workflow.modules;

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
        SendSMS sendSMS = new SendSMS(targetNumber, context);
        sendSMS.sendMessage(text);
        //Log.d("lalala", smsSent + "");
        Log.d("lalala", "sms sent");
        Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
        Log.d("lalala", "1");
        context.sendBroadcast(executeModule);
        Log.d("lalala", "3");
    }

    @Override
    public void stopExecution() {

    }
}
