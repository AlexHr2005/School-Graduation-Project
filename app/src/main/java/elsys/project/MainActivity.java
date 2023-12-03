package elsys.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WorkflowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.workflowRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        MaterialDividerItemDecoration materialDividerItemDecoration =
                new MaterialDividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        materialDividerItemDecoration.setDividerInsetStart(100);
        materialDividerItemDecoration.setDividerInsetEnd(100);
        materialDividerItemDecoration.setLastItemDecorated(false);
        recyclerView.addItemDecoration(materialDividerItemDecoration);

        List<Workflow> workflows = createSampleWorkflows();
        Resources resources = getResources();
        adapter = new WorkflowAdapter(workflows, resources);
        recyclerView.setAdapter(adapter);
    }

    private List<Workflow> createSampleWorkflows() {
        List<Workflow> workflows = new ArrayList<>();
        workflows.add(new Workflow("Workflow1"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow3"));
        workflows.add(new Workflow("Workflow4"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("Workflow2"));
        workflows.add(new Workflow("x: Workflow2"));

        return workflows;
    }
}


/*CallStateReceiver callStateReceiver = new CallStateReceiver();
        CallStateReceiver.bindHandler(this);
        IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callStateReceiver, intentFilter);*/