package elsys.project.activities.edit_workflow.modules;

import java.util.ArrayList;

public class SendSmsModule extends SmsModule{
    public String targetNumber;

    public SendSmsModule(String targetNumber, String text) {
        super(text);
        this.targetNumber = targetNumber;
    }

    @Override
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionValues = super.getOptionsValues();
        optionValues.add(0, targetNumber);
        return optionValues;
    }
}
