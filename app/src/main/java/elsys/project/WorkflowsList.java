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

        Log.d("workflow files", "workflow directory exists");
        String[] workflowNames = workflowsDir.list();
        if(workflowsDir.list().length == 0) {
            Log.d("workflow files","workflow directory is empty");
        }

        assert workflowNames != null;
        for (String workflowName : workflowNames) {
            workflows.add(new Workflow(workflowName, new File(workflowsDir, workflowName)));
        }

    }

    public static void addWorkflow() {
        //TODO: method for adding new workflow to the list
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

        Log.d("workflow names", "Source File: " + sourceFile.getAbsolutePath());
        Log.d("workflow names", "Target File: " + targetFile.getAbsolutePath());

        boolean isRenamed = sourceFile.renameTo(targetFile);
        Log.d("workflow names", newName);
        Log.d("workflow names", toBeRenamed.getName());
        if(isRenamed) {
            toBeRenamed.setName(newName);
            toBeRenamed.setWorkflowFile(targetFile);
        }
        return isRenamed;
    }
}
