package elsys.project.activities.edit_workflow.modules;

import java.time.LocalTime;

public class BackgroundAlarmModule extends AlarmModule{

    public BackgroundAlarmModule(LocalTime repetitionTime) {
        super(repetitionTime);
        subhead = Module.BACKGROUND_ALARM;
    }
}
