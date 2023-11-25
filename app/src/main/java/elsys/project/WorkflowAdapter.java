package elsys.project;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkflowAdapter extends RecyclerView.Adapter<WorkflowAdapter.WorkflowViewHolder> {
    private List<Workflow> workflows;

    public WorkflowAdapter(List<Workflow> workflows) {
        this.workflows = workflows;
    }

    @NonNull
    @Override
    public WorkflowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workflow_layout, parent, false);
        return new WorkflowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkflowViewHolder holder, int position) {
        Workflow workflow = workflows.get(position);

        holder.workflowName.setText(workflow.getName());

        holder.optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workflows.size();
    }

    public static class WorkflowViewHolder extends RecyclerView.ViewHolder {
        public TextView workflowName;
        public ImageButton optionsButton;

        public WorkflowViewHolder(@NonNull View itemView) {
            super(itemView);
            workflowName = itemView.findViewById(R.id.workflowName);
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

                if(R.id.menu_option1 == item_id) {
                    //TODO Handle option 1
                    return true;
                }
                else if (R.id.menu_option2 == item_id) {
                    //TODO Handle option 2
                    return true;
                }
                else if (R.id.menu_option3 == item_id) {
                    //TODO Handle option 3
                    return true;
                }
                else return false;
            }
        });

        popupMenu.show();
    }
}
