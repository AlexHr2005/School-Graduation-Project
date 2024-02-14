package elsys.project.activities.edit_workflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import elsys.project.CryptoManager;
import elsys.project.R;
import elsys.project.Workflow;
import elsys.project.WorkflowAdapter;
import elsys.project.WorkflowsList;
import elsys.project.activities.MainActivity;
import elsys.project.activities.edit_workflow.modules.Module;
import elsys.project.modules.phone_calls.CallReceiver;

public class EditWorkflowActivity extends AppCompatActivity {
    public static ArrayList<Module> modules;
    public RecyclerView recyclerView;
    public static ModuleAdapter adapter;
    public String workflowName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workflow);

        modules = new ArrayList<>(0);
        recyclerView = findViewById(R.id.moduleRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ModuleAdapter();
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        workflowName = intent.getStringExtra("Workflow name");
    }

    public void createModule(View view) {
        FullScreenDialog fullScreenDialog = new FullScreenDialog();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, fullScreenDialog)
                .addToBackStack(null).commit();
    }

    public void cancelActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveWorkflowToFile(View view) throws IOException {
        Workflow workflow = WorkflowsList.getWorkflowByName(workflowName);
        File workflowFile = workflow.getWorkflowFile();
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(workflowFile, true));

        String fileContent = "";

        for(int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            String line = module.title + ":" + module.subhead + "(";
            ArrayList<String> optionValues = module.getOptionsValues();
            for(int j = 0; j < optionValues.size(); j++) {
                line += optionValues.get(j);
                if(j != optionValues.size() - 1) {
                    line += ";";
                }
            }
            line += ")";
            //bufferedWriter.write(line);
            fileContent += line;
            if(i != modules.size() - 1) {
                fileContent += "\n";
                //bufferedWriter.newLine();
            }
        }
        byte[] encrypted = CryptoManager.encrypt(workflowName, fileContent);
        Log.d("encryption", fileContent);
        //Log.d("encryption", encrypted);

        try (FileOutputStream outputStream = new FileOutputStream(workflowFile)) {
            outputStream.write(encrypted);
        }

        //bufferedWriter.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
