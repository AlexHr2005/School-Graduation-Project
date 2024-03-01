package elsys.project.activities.build_workflow.modules;

import java.util.ArrayList;

public abstract class SmsModule extends Module {
    public String text;

    @Override
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionsValues = new ArrayList<>(0);
        optionsValues.add(text);
        return optionsValues;
    }

    public SmsModule(String text) {
        title = SMS;
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
