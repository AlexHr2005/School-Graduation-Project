package elsys.project.activities.edit_workflow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import elsys.project.R;

public class FullScreenDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_module_dialog, container, false);

        AutoCompleteTextView module_type = view.findViewById(R.id.moduleTypeMenu);
        AutoCompleteTextView module_subtype = view.findViewById(R.id.moduleSubtypeMenu);

        module_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] items;
                ArrayAdapter<String> adapter = null;

                String pickedItem = (String) adapterView.getItemAtPosition(i);
                String[] moduleTypes = getResources().getStringArray(R.array.module_types);

                if(pickedItem.equals(moduleTypes[0])) {
                    items = getResources().getStringArray(R.array.alarm_types);
                    adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, items);
                }
                else if(pickedItem.equals(moduleTypes[1])) {
                    items = getResources().getStringArray(R.array.SMS_types);
                    adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, items);
                }
                else if(pickedItem.equals(moduleTypes[2])){
                    items = getResources().getStringArray(R.array.phone_call_types);
                    adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, items);
                }
                module_subtype.setText("");
                module_subtype.setAdapter(adapter);
            }

        });
        return view;
    }
}
