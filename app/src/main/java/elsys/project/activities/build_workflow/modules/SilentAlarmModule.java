package elsys.project.activities.build_workflow.modules;

import java.time.LocalTime;

public class SilentAlarmModule extends AlarmModule{

    public SilentAlarmModule(LocalTime repetitionTime) {
        super(repetitionTime);
        subhead = Module.SILENT_ALARM;
    }
}
