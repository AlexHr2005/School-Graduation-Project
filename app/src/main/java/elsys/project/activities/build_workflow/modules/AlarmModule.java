package elsys.project.activities.edit_workflow.modules;

import android.util.Log;

import java.time.LocalTime;
import java.util.ArrayList;

import elsys.project.BroadcastReceiversManager;
import elsys.project.activities.AlarmRingActivity;
import elsys.project.modules.alarms.Alarm;

public abstract class AlarmModule extends Module {
    public LocalTime repetitionTime;
    private Alarm alarm = null;

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
        BroadcastReceiversManager.registerAlarmReceiver();
        // bottom line requires API >= 26
        alarm = new Alarm(repetitionTime.getHour(), repetitionTime.getMinute());
        Log.d("lalala", "alarm to be scheduled for " + subhead);
        Alarm.requestCode++;
        alarm.schedule(context, subhead);
    }

    @Override
    public void stopExecution() {
        BroadcastReceiversManager.unregisterAlarmReceiver();
        alarm.cancel();
        AlarmRingActivity.cancelSnoozedAlarm();
        Log.d("lalala", "alarm module stopped");
    }
}
