package elsys.project.activities.edit_workflow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import elsys.project.R;

public class ModuleAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_card, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        public TextView moduleTitle;
        public TextView moduleSubhead;
        public ArrayList<TextView> moduleOptions;
        public TextView moduleOption1;
        public TextView moduleOption2;
        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleTitle = itemView.findViewById(R.id.module_title);
            moduleSubhead = itemView.findViewById(R.id.module_subhead);
            moduleOption1 = itemView.findViewById(R.id.module_option_field_1);
            moduleOption2 = itemView.findViewById(R.id.module_option_field_2);
            moduleOptions.add(moduleOption1);
            moduleOptions.add(moduleOption2);
        }
    }
}
