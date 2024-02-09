package elsys.project;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class WorkflowsList {
    private static File workflowsDir;
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

    public static int getWorkflowsCount() {
        return workflows.size();
    }

    public static void setWorkflowsDir(File workflowsDir) {
        WorkflowsList.workflowsDir = workflowsDir;
    }

    public static File getWorkflowsDir() {
        return workflowsDir;
    }

    public static void loadWorkflows(Context context) {
        workflows.clear();
        File filesDir = context.getFilesDir();
        File workflowsDir = new File(filesDir, String.valueOf(R.string.workflows_dir));

        String[] workflowNames = workflowsDir.list();

        assert workflowNames != null;
        for (String workflowName : workflowNames) {
            workflows.add(new Workflow(workflowName, new File(workflowsDir, workflowName)));
        }

    }

    public static void addWorkflow(File file) {
        Workflow workflow = new Workflow(file.getName());
        workflows.add(workflow);
    }

    public static boolean deleteWorkflow(String workflowName) {
        return new Workflow(workflowName).getWorkflowFile().delete();
    }

    public static void removeFromList(String workflowName) {
        workflows.removeIf(workflow -> workflow.getName().equals(workflowName));
    }

    public static boolean renameWorkflow(String workflowName, String newName) {
        Workflow toBeRenamed = (Workflow) workflows.stream().filter(workflow -> workflow.getName().equals(workflowName)).toArray()[0];
        File sourceFile = toBeRenamed.getWorkflowFile();
        File targetFile = new File(workflowsDir, newName);

        boolean isRenamed = sourceFile.renameTo(targetFile);
        if(isRenamed) {
            toBeRenamed.setName(newName);
            toBeRenamed.setWorkflowFile(targetFile);
        }
        return isRenamed;
    }

    public static StringBuilder getWorkflowContent(String workflowName) {
        return getWorkflowByName(workflowName).readFile();
    }

    public static int size() {
        return workflows.size();
    }

}
