package elsys.project.activities.edit_workflow;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import elsys.project.R;
import elsys.project.activities.edit_workflow.modules.Module;
import elsys.project.activities.edit_workflow.modules.SendSmsModule;
import elsys.project.activities.edit_workflow.modules.SmsModule;
import elsys.project.activities.edit_workflow.modules.SmsReceiverModule;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {
    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_card, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = EditWorkflowActivity.modules.get(position);

        holder.moduleTitle.setText(module.title);
        holder.moduleSubhead.setText(module.subhead);

        ArrayList<String> optionsValues = module.getOptionsValues();

        for (int i = 0; i < optionsValues.size(); i++) {
            String value = optionsValues.get(i);
            holder.moduleOptions.get(i).setText(value);
        }

        holder.deleteModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditWorkflowActivity.modules.remove(holder.getAdapterPosition());
                EditWorkflowActivity.adapter.notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return EditWorkflowActivity.modules.size();
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        public TextView moduleTitle;
        public TextView moduleSubhead;
        public ArrayList<TextView> moduleOptions;
        public TextView moduleOption1;
        public TextView moduleOption2;
        public ImageView deleteModule;
        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleTitle = itemView.findViewById(R.id.module_title);
            moduleSubhead = itemView.findViewById(R.id.module_subhead);
            moduleOption1 = itemView.findViewById(R.id.module_option_field_1);
            moduleOption2 = itemView.findViewById(R.id.module_option_field_2);
            deleteModule = itemView.findViewById(R.id.deleteModule);
            moduleOptions = new ArrayList<>(0);
            moduleOptions.add(moduleOption1);
            moduleOptions.add(moduleOption2);
        }
    }
}
