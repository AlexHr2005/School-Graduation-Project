package elsys.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import elsys.project.modules.phone_calls.CallRecordRetriever;
import elsys.project.modules.phone_calls.CallStateReceiver;
import elsys.project.modules.phone_calls.NewCallHandler;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WorkflowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.workflowRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Workflow> workflows = createSampleWorkflows();
        adapter = new WorkflowAdapter(workflows);
        recyclerView.setAdapter(adapter);
    }

    private List<Workflow> createSampleWorkflows() {
        List<Workflow> workflows = new ArrayList<>();
        workflows.add(new Workflow("Workflow1"));
        workflows.add(new Workflow("Workflow2"));
        return workflows;
    }
}


/*CallStateReceiver callStateReceiver = new CallStateReceiver();
        CallStateReceiver.bindHandler(this);
        IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callStateReceiver, intentFilter);*/