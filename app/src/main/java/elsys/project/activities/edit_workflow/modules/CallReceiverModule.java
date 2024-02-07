package elsys.project.activities.edit_workflow.modules;

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
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionValues = super.getOptionsValues();
        optionValues.add(callerNumber);
        return optionValues;
    }
}
