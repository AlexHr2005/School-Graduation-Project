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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import elsys.project.R;
import elsys.project.Workflow;
import elsys.project.WorkflowAdapter;
import elsys.project.WorkflowsList;
import elsys.project.activities.MainActivity;
import elsys.project.activities.edit_workflow.modules.Module;

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
        Log.d("Workflows file lines", "0.1");
        workflowName = intent.getStringExtra("Workflow name");
        Log.d("Workflows file lines", workflowName);
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
        Log.d("Workflows file lines", "1");
        Workflow workflow = WorkflowsList.getWorkflowByName(workflowName);
        File workflowFile = workflow.getWorkflowFile();
        Log.d("Workflows file lines", workflowFile.toString());
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(workflowFile, true));

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
            bufferedWriter.write(line);
            if(i != modules.size() - 1) {
                bufferedWriter.newLine();
            }
            //Log.d("Workflows file lines", line);
        }
        bufferedWriter.close();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}