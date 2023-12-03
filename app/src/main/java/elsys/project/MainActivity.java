package elsys.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WorkflowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkflowsList.loadWorkflows(this);

        recyclerView = findViewById(R.id.workflowRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        MaterialDividerItemDecoration materialDividerItemDecoration =
                new MaterialDividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        materialDividerItemDecoration.setDividerInsetStart(100);
        materialDividerItemDecoration.setDividerInsetEnd(100);
        materialDividerItemDecoration.setLastItemDecorated(false);
        recyclerView.addItemDecoration(materialDividerItemDecoration);

        Resources resources = getResources();
        adapter = new WorkflowAdapter(resources);
        recyclerView.setAdapter(adapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.add_workflow);

    }
}


/*CallStateReceiver callStateReceiver = new CallStateReceiver();
        CallStateReceiver.bindHandler(this);
        IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callStateReceiver, intentFilter);*/