package elsys.project.activities.edit_workflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import elsys.project.R;
import elsys.project.WorkflowAdapter;
import elsys.project.activities.MainActivity;
import elsys.project.activities.edit_workflow.modules.Module;

public class EditWorkflowActivity extends AppCompatActivity {
    public static ArrayList<Module> modules;
    public RecyclerView recyclerView;
    public static ModuleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workflow);

        modules = new ArrayList<>(0);
        recyclerView = findViewById(R.id.moduleRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ModuleAdapter();
        recyclerView.setAdapter(adapter);
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

    public void saveWorkflowToFile(View view) {

    }
}