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
        String[] moduleTitles = context.getResources().getStringArray(R.array.module_titles);

        String fileContent = readFile().toString();
        String[] lines = fileContent.split("\n");

        ArrayList<Module> modules = new ArrayList<>(0);
        for(String line : lines) {
            String[] lineParts = line.split("[:();]");
            Module currModule = null;
            Log.d("workflow file read", line);

            if(lineParts[0].equals(moduleTitles[0])) {
                String[] alarmSubheads = context.getResources().getStringArray(R.array.alarm_subheads);
                int hours = Integer.parseInt(lineParts[2]);
                int minutes = Integer.parseInt(lineParts[3]);
                if(lineParts[1].equals(alarmSubheads[0])) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        currModule = new BackgroundAlarmModule(LocalTime.of(hours, minutes));
                    }
                }
                else if(lineParts[2].equals(alarmSubheads[1])) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        currModule = new SoundAlarmModule(LocalTime.of(hours, minutes));
                    }
                }
            }
            else if(lineParts[0].equals(moduleTitles[1])) {
                String[] smsSubheads = context.getResources().getStringArray(R.array.SMS_subheads);
                String number = lineParts[2];
                String text = lineParts[3];
                if(lineParts[1].equals(smsSubheads[0])) {
                    currModule = new SendSmsModule(number, text);
                }
                else if(lineParts[1].equals(smsSubheads[1])) {
                    currModule = new SmsReceiverModule(number, text);
                }
            }
            else if(lineParts[0].equals(moduleTitles[2])) {
                String[] phoneCallSubheads = context.getResources().getStringArray(R.array.phone_call_subheads);
                if(lineParts[1].equals(phoneCallSubheads[0])) {
                    currModule = new CallReceiverModule();
                }
            }
            modules.add(currModule);
            ModuleToExecute.setModules(modules);
            Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
            context.sendBroadcast(executeModule);
        }
    }
}