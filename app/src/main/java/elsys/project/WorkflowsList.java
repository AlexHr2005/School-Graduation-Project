package elsys.project;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

public class WorkflowsList {
    private static ArrayList<Workflow> workflows = new ArrayList<>(0);

    public static ArrayList<Workflow> getWorkflows() {
        return workflows;
    }

    public static Workflow getWorkflowByPosition(int position) {
        return workflows.get(position);
    }

    public static Workflow getWorkflowByName(String name) {
        return (Workflow) workflows.stream().filter(workflow -> workflow.getName().equals(name)).toArray()[0];
    }

    public static int workflowsCount() {
        return workflows.size();
    }

    public static void loadWorkflows(Context context) {
        File filesDir = context.getFilesDir();
        File workflowsDir = new File(filesDir, String.valueOf(R.string.workflows_dir));

        if(workflowsDir.exists()) {
            String[] workflowNames = workflowsDir.list();

            assert workflowNames != null;
            for (String workflowName : workflowNames) {
                workflows.add(new Workflow(workflowName, new File(workflowsDir, workflowName)));
            }
        }
    }

    public static void addWorkflow() {
        //TODO: method for adding new workflow to the list
    }
}
