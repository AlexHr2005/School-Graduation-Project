package elsys.project.activities.edit_workflow.modules;

import java.util.ArrayList;

import elsys.project.R;

public abstract class Module {
    //Title can be: ALARM, PHONE CALL, SMS
    public String title;
    public String subhead;

    public abstract ArrayList<String> getOptionsValues();

    public abstract void execute();
}
