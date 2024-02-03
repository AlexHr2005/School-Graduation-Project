package elsys.project.activities.edit_workflow.modules;

import android.util.Log;

import java.time.LocalTime;
import java.util.ArrayList;

import elsys.project.activities.edit_workflow.modules.Module;
import elsys.project.modules.alarms.Alarm;

public abstract class AlarmModule extends Module {
    public LocalTime repetitionTime;

    public AlarmModule(LocalTime repetitionTime) {
        this.repetitionTime = repetitionTime;
        title = Module.ALARM;
    }

    @Override
    public ArrayList<String> getOptionsValues() {
        ArrayList<String> optionsValues = new ArrayList<>(0);
        optionsValues.add(repetitionTime.toString());
        return optionsValues;
    }

    @Override
    public void execute() {
        Alarm alarm = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            alarm = new Alarm(repetitionTime.getHour(), repetitionTime.getMinute());
        }
        Log.d("lalala", "alarm to be scheduled for " + subhead);
        Alarm.requestCode++;
        alarm.schedule(context, subhead);
    }
}
