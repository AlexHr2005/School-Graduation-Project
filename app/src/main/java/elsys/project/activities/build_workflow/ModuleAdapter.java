package elsys.project.activities.build_workflow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import elsys.project.R;
import elsys.project.activities.build_workflow.modules.Module;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {
    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_card, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = BuildWorkflowActivity.modules.get(position);

        holder.moduleTitle.setText(module.title);
        holder.moduleSubhead.setText(module.subhead);

        ArrayList<String> optionsValues = module.getOptionsValues();

        for (int i = 0; i < holder.moduleOptions.size(); i++) {
            if(i >= optionsValues.size()) {
                holder.moduleOptions.get(i).setVisibility(View.GONE);
            }
            else {
                String value = optionsValues.get(i);
                holder.moduleOptions.get(i).setText(value);
            }
        }

        holder.deleteModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildWorkflowActivity.modules.remove(holder.getAdapterPosition());
                BuildWorkflowActivity.adapter.notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return BuildWorkflowActivity.modules.size();
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
