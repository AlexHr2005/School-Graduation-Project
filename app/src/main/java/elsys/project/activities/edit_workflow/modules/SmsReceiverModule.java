package elsys.project.activities.edit_workflow.modules;

import java.util.ArrayList;

public class SmsReceiverModule extends SmsModule{
    public String senderNumber;

    public SmsReceiverModule(String text, String senderNumber) {
        super(text);
        this.senderNumber = senderNumber;
    }

    @Override
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionValues = super.getOptionsValues();
        optionValues.add(senderNumber);
        return optionValues;
    }
}
