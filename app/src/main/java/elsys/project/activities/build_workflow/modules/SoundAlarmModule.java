package elsys.project.activities.build_workflow.modules;

import java.time.LocalTime;

public class SoundAlarmModule extends AlarmModule{

    public SoundAlarmModule(LocalTime repetitionTime) {
        super(repetitionTime);
        subhead = SOUND_ALARM;
    }
}
