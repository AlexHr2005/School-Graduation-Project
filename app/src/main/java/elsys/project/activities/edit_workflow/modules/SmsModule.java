package elsys.project.activities.edit_workflow.modules;

import java.util.ArrayList;

import elsys.project.activities.edit_workflow.modules.Module;

public abstract class SmsModule extends Module {
    public String Text;

    @Override
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionsValues = new ArrayList<>(0);
        optionsValues.add(Text);
        return optionsValues;
    }

    public SmsModule(String text) {
        Text = text;
    }

    public String getText() {
        return Text;
    }
}
