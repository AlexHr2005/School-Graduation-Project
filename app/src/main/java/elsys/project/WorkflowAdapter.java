package elsys.project;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

public class WorkflowAdapter extends RecyclerView.Adapter<WorkflowAdapter.WorkflowViewHolder> {
    Resources resources;

    public WorkflowAdapter(Resources resources) {
        this.resources =  resources;
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

        holder.workflowNameView.setText(workflow.getName());

        holder.optionsButton.setOnClickListener(view -> showPopupMenu(view));

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
        return WorkflowsList.workflowsCount();
    }

    public static class WorkflowViewHolder extends RecyclerView.ViewHolder {
        public TextView workflowNameView;
        public MaterialButton optionsButton;

        public WorkflowViewHolder(@NonNull View itemView) {
            super(itemView);
            workflowNameView = itemView.findViewById(R.id.workflowNameView);
            optionsButton = itemView.findViewById(R.id.optionsButton);
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.workflow_options, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int item_id = menuItem.getItemId();

                if(R.id.delete_option == item_id) {
                    //TODO Handle option
                    return true;
                }
                else if (R.id.edit_option == item_id) {
                    //TODO Handle option
                    return true;
                }
                else if (R.id.rename_option == item_id) {
                    //TODO Handle option
                    return true;
                }
                else return false;
            }
        });

        popupMenu.show();
    }
}
