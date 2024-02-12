package elsys.project;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

import elsys.project.activities.edit_workflow.modules.BackgroundAlarmModule;
import elsys.project.activities.edit_workflow.modules.CallReceiverModule;
import elsys.project.activities.edit_workflow.modules.Module;
import elsys.project.activities.edit_workflow.modules.ModuleToExecute;
import elsys.project.activities.edit_workflow.modules.SendSmsModule;
import elsys.project.activities.edit_workflow.modules.SmsReceiverModule;
import elsys.project.activities.edit_workflow.modules.SoundAlarmModule;

public class Workflow {
    private String name;
    private File workflowFile;
    private boolean running = false;
    private boolean accessible = true;


    public Workflow(String name, File workflowFile) {
        this.name = name;
        this.workflowFile = workflowFile;
    }

    public Workflow(String name) {
        this.name = name;
        this.workflowFile = new File(WorkflowsList.getWorkflowsDir(), name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getWorkflowFile() {
        return workflowFile;
    }

    public void setWorkflowFile(File workflowFile) {
        this.workflowFile = workflowFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workflow workflow = (Workflow) o;
        return name.equals(workflow.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public StringBuilder readFile() {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream fileInputStream = new FileInputStream(workflowFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder;
    }

    public void run(Context context) {
        String fileContent = readFile().toString();
        String[] lines = fileContent.split("\n");

        ArrayList<Module> modules = new ArrayList<>(0);
        Module currModule = null;
        for(String line : lines) {
            line += "a";
            String[] lineParts = line.split("[:();]");
            Log.d("lalala", line);
            for(String linePart : lineParts) {
                Log.d("lalala", linePart);
            }
            Log.d("lalala", lineParts.length + "");

            if(lineParts[0].equals(Module.ALARM)) {
                Log.d("lalala", "it is alarm");
                int hours = Integer.parseInt(lineParts[2]);
                int minutes = Integer.parseInt(lineParts[3]);
                if(lineParts[1].equals(Module.BACKGROUND_ALARM)) {
                    Log.d("lalala", "it is background alarm");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        currModule = new BackgroundAlarmModule(LocalTime.of(hours, minutes));
                    }
                }
                else if(lineParts[1].equals(Module.SOUND_ALARM)) {
                    Log.d("lalala", "it is sound alarm");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        currModule = new SoundAlarmModule(LocalTime.of(hours, minutes));
                    }
                }
            }
            else if(lineParts[0].equals(Module.SMS)) {
                String number = lineParts[2];
                String text = lineParts[3];
                if(lineParts[1].equals(Module.SEND_SMS)) {
                    currModule = new SendSmsModule(number, text);
                }
                else if(lineParts[1].equals(Module.RECEIVE_SMS)) {
                    currModule = new SmsReceiverModule(number, text, context);
                }
            }
            else if(lineParts[0].equals(Module.PHONE_CALL)) {
                if(lineParts[1].equals(Module.RECEIVE_PHONE_CALL)) {
                    String number = lineParts[2];
                    currModule = new CallReceiverModule(number);
                }
            }
            modules.add(currModule);
            Log.d("lalala", "module added");
        }
        BroadcastReceiversManager.registerModuleExecutionReceiver();
        ModuleToExecute.setModules(modules);
        Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
        context.sendBroadcast(executeModule);
        Log.d("lalala", "end");
    }

    public void stop(Context context) {
        Log.d("lalala", "stop workflow");
        Intent stopWorkflow = new Intent("project.elsys.STOP_WORKFLOW");
        context.sendBroadcast(stopWorkflow);
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }
}