package elsys.project.activities.edit_workflow.modules;

import java.time.LocalTime;

public class SilentAlarmModule extends AlarmModule{

    public SilentAlarmModule(LocalTime repetitionTime) {
        super(repetitionTime);
        subhead = Module.SILENT_ALARM;
    }
}
