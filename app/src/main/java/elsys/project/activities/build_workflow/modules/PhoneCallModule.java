package elsys.project.activities.build_workflow.modules;

import java.util.ArrayList;

public abstract class PhoneCallModule extends Module {
    @Override
    public ArrayList<String> getOptionsValues() {
        return new ArrayList<>(0);
    }

    public PhoneCallModule() {
        this.title = Module.PHONE_CALL;
    }
}
