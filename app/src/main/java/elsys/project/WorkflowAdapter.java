package elsys.project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class WorkflowAdapter extends RecyclerView.Adapter<WorkflowAdapter.WorkflowViewHolder> {
    public Resources resources;
    public Context context;


    public WorkflowAdapter(Resources resources, Context context) {
        this.resources = resources;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkflowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workflow_layout, parent, false);
        return new WorkflowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkflowViewHolder holder, int position) {
        Workflow workflow = WorkflowsList.getWorkflowByPosition(position);
        if(workflow.isRunning()) {
            holder.startOrStopButton.setIcon(AppCompatResources.getDrawable(context, R.drawable.stop_workflow_icon));
        }
        else holder.startOrStopButton.setIcon(AppCompatResources.getDrawable(context, R.drawable.start_workflow_icon));

        holder.startOrStopButton.setEnabled(workflow.isAccessible());

        holder.workflowNameView.setText(workflow.getName());

        holder.optionsButton.setOnClickListener(view -> showPopupMenu(view, holder));

        holder.startOrStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!workflow.isRunning()) {
                    if(workflow.run(context.getApplicationContext())) {
                        workflow.setRunning(true);
                        for (int i = 0; i < WorkflowsList.size(); i++) {
                            if (i != position) {
                                WorkflowsList.getWorkflowByPosition(i).setAccessible(false);
                            }
                        }
                    }
                }
                else {
                    workflow.setRunning(false);
                    for(int i = 0; i < WorkflowsList.size(); i++) {
                        if(i != position) {
                            WorkflowsList.getWorkflowByPosition(i).setAccessible(true);
                        }
                    }
                    workflow.stop(context.getApplicationContext());
                }
                notifyDataSetChanged();
            }
        });

        if(position == getItemCount() - 1) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            int marginBottom = resources.getDimensionPixelSize(R.dimen.recyclerview_last_item_margin_bottom);
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, marginBottom);
            holder.itemView.setLayoutParams(params);
        }
        else {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, 0);
            holder.itemView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return WorkflowsList.getWorkflowsCount();
    }

    public static class WorkflowViewHolder extends RecyclerView.ViewHolder {
        public TextView workflowNameView;
        public MaterialButton optionsButton;
        public MaterialButton startOrStopButton;

        public WorkflowViewHolder(@NonNull View itemView) {
            super(itemView);
            workflowNameView = itemView.findViewById(R.id.workflowNameView);
            optionsButton = itemView.findViewById(R.id.optionsButton);
            startOrStopButton = itemView.findViewById(R.id.startOrStopWorkflow);
        }
    }

    private void showPopupMenu(View view, WorkflowViewHolder holder) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.workflow_options, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int item_id = menuItem.getItemId();

            if(R.id.delete_option == item_id) {
                String workflowName = holder.workflowNameView.getText().toString();
                boolean isDeleted = WorkflowsList.deleteWorkflowFile(workflowName);
                if(isDeleted) {
                    WorkflowsList.removeFromList(workflowName);
                    notifyItemRemoved(holder.getAdapterPosition());
                }
                return true;
            }
            else if (R.id.rename_option == item_id) {
                final EditText input = new EditText(context);
                input.setText(holder.workflowNameView.getText());
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                AlertDialog dialog = new MaterialAlertDialogBuilder(context)
                        .setTitle(R.string.workflow_name_dialog_title)
                        .setView(input)
                        .setNeutralButton(R.string.workflow_name_dialog_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton(R.string.workflow_name_dialog_rename, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String workflowName = holder.workflowNameView.getText().toString();
                                boolean isRenamed = WorkflowsList.renameWorkflow(workflowName, input.getText().toString());
                                if(isRenamed) {
                                    notifyItemChanged(holder.getAdapterPosition());
                                }
                            }
                        })
                        .show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
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
                return true;
            }
            else return false;
        });

        popupMenu.show();
    }
}