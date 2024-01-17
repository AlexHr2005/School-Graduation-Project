package elsys.project.activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.InputType;
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
import elsys.project.activities.edit_workflow.EditWorkflowActivity;

public class MainActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public WorkflowAdapter adapter;
    public Context context = this;
    public ActivityResultLauncher<String> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //WorkflowsList.loadWorkflows(this);

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

        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                if(o != null) {
                    moveFile(o);
                }
            }
        });

    }

    public void createWorkflow(View view) {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Create new workflow")
                .setView(input)
                .setNeutralButton(R.string.workflow_name_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton(R.string.workflow_name_dialog_proceed, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, EditWorkflowActivity.class);
                        Log.d("Workflows file lines", String.valueOf(input.getText()));
                        intent.putExtra("Workflow name", input.getText().toString());
                        Log.d("Workflows file lines", "12");
                        startActivity(intent);
                        File newFile = new File(WorkflowsList.getWorkflowsDir(), String.valueOf(input.getText()));
                        try {
                            if(newFile.createNewFile()) {
                                WorkflowsList.loadWorkflows(context);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .show();
    }

    public void importWorkflow(View view) {
        filePickerLauncher.launch("text/*");
    }

    private void moveFile(Uri sourceUri) {
        File destinationDir = WorkflowsList.getWorkflowsDir();

        try {
            //TODO: check for existence of files
            InputStream inputStream = getContentResolver().openInputStream(sourceUri);
            if(inputStream != null) {
                File destinationFile = new File(destinationDir, getFileNameFromUri(sourceUri));
                OutputStream outputStream = new FileOutputStream(destinationFile);

                byte[] buffer = new byte[1024];
                int bytesRead;

                while((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outputStream.close();

                Toast.makeText(this, "File imported successfully!", Toast.LENGTH_SHORT).show();

                WorkflowsList.loadWorkflows(this);
                adapter.notifyDataSetChanged();
            }
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(this, "Error importing file", Toast.LENGTH_LONG).show();
        }
    }

    private String getFileNameFromUri(Uri uri) {
        Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        returnCursor.moveToFirst();
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        String fileName = returnCursor.getString(nameIndex);
        returnCursor.close();
        int extensionIndex = fileName.lastIndexOf(".txt");
        if(extensionIndex != -1) {
            return fileName.substring(0, extensionIndex);
        }
        return fileName;
    }
}


/*CallStateReceiver callStateReceiver = new CallStateReceiver();
        CallStateReceiver.bindHandler(this);
        IntentFilter intentFilter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callStateReceiver, intentFilter);*/