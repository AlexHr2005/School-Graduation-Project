package elsys.project;

import android.content.Context;
import android.content.Intent;
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

import elsys.project.activities.build_workflow.modules.SilentAlarmModule;
import elsys.project.activities.build_workflow.modules.CallReceiverModule;
import elsys.project.activities.build_workflow.modules.Module;
import elsys.project.activities.build_workflow.modules.ModuleExecutionReceiver;
import elsys.project.activities.build_workflow.modules.SendSmsModule;
import elsys.project.activities.build_workflow.modules.SmsReceiverModule;
import elsys.project.activities.build_workflow.modules.SoundAlarmModule;

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


    private byte[] getBytesFromFile() throws IOException {
        byte[] fileContent = new byte[(int) workflowFile.length()];
        try(FileInputStream fis = new FileInputStream(workflowFile)) {
            fis.read(fileContent);
        }
        return fileContent;
    }

    public boolean run(Context context) {
        try {
            byte[] encryptedBytes = null;
            encryptedBytes = getBytesFromFile();
            String decrypted = new String(CryptoManager.decrypt(encryptedBytes, name));
            String[] lines = decrypted.split("\n");

            ArrayList<Module> modules = new ArrayList<>(0);
            Module currModule = null;
            for(String line : lines) {
                line += "a";
                String[] lineParts = line.split("[:();]");
                for(String linePart : lineParts) {
                    Log.d("lalala", linePart);
                }
                Log.d("lalala", lineParts.length + "");

                if(lineParts[0].equals(Module.ALARM)) {
                    Log.d("lalala", "it is alarm");
                    int hours = Integer.parseInt(lineParts[2]);
                    int minutes = Integer.parseInt(lineParts[3]);
                    if(lineParts[1].equals(Module.SILENT_ALARM)) {
                        Log.d("lalala", "it is background alarm");
                        currModule = new SilentAlarmModule(LocalTime.of(hours, minutes));
                    }
                    else if(lineParts[1].equals(Module.SOUND_ALARM)) {
                        Log.d("lalala", "it is sound alarm");
                        currModule = new SoundAlarmModule(LocalTime.of(hours, minutes));
                    }
                }
                else if(lineParts[0].equals(Module.SMS)) {
                    String number = lineParts[2];
                    String text = lineParts[3];
                    if(lineParts[1].equals(Module.SEND_SMS)) {
                        currModule = new SendSmsModule(number, text);
                    }
                    else if(lineParts[1].equals(Module.RECEIVE_SMS)) {
                        currModule = new SmsReceiverModule(number, text);
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
            ModuleExecutionReceiver.setModules(modules);
            Intent executeModule = new Intent("project.elsys.EXECUTE_MODULE");
            context.sendBroadcast(executeModule);
            Log.d("lalala", "end");
            return true;
        }
        catch (Exception e) {
            Toast.makeText(context, "There was a problem starting the workflow.", Toast.LENGTH_SHORT).show();
            return false;
        }
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