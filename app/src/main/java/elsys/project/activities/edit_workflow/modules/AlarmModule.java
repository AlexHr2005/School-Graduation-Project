package elsys.project.activities.edit_workflow.modules;

import android.util.Log;

import java.time.LocalTime;
import java.util.ArrayList;

import elsys.project.activities.edit_workflow.modules.Module;

public abstract class AlarmModule extends Module {
    public LocalTime repetitionTime;

    public AlarmModule(LocalTime repetitionTime) {
        this.repetitionTime = repetitionTime;
    }

    @Override
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionsValues = new ArrayList<>(0);
        String repetitionTimeString = repetitionTime.getHour() + " hours, " + repetitionTime.getMinute() + " minutes";
        optionsValues.add(repetitionTimeString);
        return optionsValues;
    }
}
