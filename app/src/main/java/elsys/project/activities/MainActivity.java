package elsys.project.activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import elsys.project.R;
import elsys.project.WorkflowAdapter;
import elsys.project.WorkflowsList;
import elsys.project.activities.build_workflow.BuildWorkflowActivity;

public class MainActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public WorkflowAdapter adapter;
    public Context context = this;
    public ActivityResultLauncher<String> filePickerLauncher;

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

        adapter = new WorkflowAdapter(getResources(), this);
        recyclerView.setAdapter(adapter);
    }

    public void createWorkflow(View view) {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        AlertDialog dialog = new MaterialAlertDialogBuilder(this)
                .setTitle("Create new workflow")
                .setView(input)
                .setNeutralButton(R.string.workflow_name_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton(R.string.workflow_name_dialog_proceed, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, BuildWorkflowActivity.class);
                        intent.putExtra("Workflow name", input.getText().toString());
                        startActivity(intent);
                    }
                })
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(input.getText().toString().trim().isEmpty()) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
                else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}