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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

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
                String[] moduleTitles = getResources().getStringArray(R.array.module_titles);

                if(pickedItem.equals(moduleTitles[0])) {
                    items = getResources().getStringArray(R.array.alarm_subheads);
                    adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, items);
                }
                else if(pickedItem.equals(moduleTitles[1])) {
                    items = getResources().getStringArray(R.array.SMS_subheads);
                    adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, items);
                }
                else if(pickedItem.equals(moduleTitles[2])){
                    items = getResources().getStringArray(R.array.phone_call_subheads);
                    adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, items);
                }
                module_subtype.setText("");
                module_subtype.setAdapter(adapter);
            }

        });

        ArrayList<TextInputLayout> optionsLayouts = new ArrayList<>(0);
        assert container != null;
        optionsLayouts.add(view.findViewById(R.id.moduleOption1Layout));
        optionsLayouts.add(view.findViewById(R.id.moduleOption2Layout));

        module_subtype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for(TextInputLayout optionLayout : optionsLayouts) {
                    optionLayout.setEnabled(false);
                    optionLayout.setHint("");
                }

                String pickedItem = (String) adapterView.getItemAtPosition(i);
                String[] alarmSubheads = getResources().getStringArray(R.array.alarm_subheads);
                String[] smsSubheads = getResources().getStringArray(R.array.SMS_subheads);
                String[] phoneCallSubheads = getResources().getStringArray(R.array.phone_call_subheads);

                String[] optionsHints;

                if(pickedItem.equals(alarmSubheads[0])) {
                    optionsHints = getResources().getStringArray(R.array.background_alarm_options);
                }
                else if(pickedItem.equals(alarmSubheads[1])) {
                    optionsHints = getResources().getStringArray(R.array.sound_alarm_options);
                }
                else if(pickedItem.equals(smsSubheads[0])) {
                    optionsHints = getResources().getStringArray(R.array.SMS_send_options);
                }
                else if(pickedItem.equals(smsSubheads[1])) {
                    optionsHints = getResources().getStringArray(R.array.SMS_receive_options);
                }
                else if(pickedItem.equals(phoneCallSubheads[0])) {
                    optionsHints = getResources().getStringArray(R.array.phone_call_log_options);
                }
                else if(pickedItem.equals(phoneCallSubheads[1])) {
                    optionsHints = getResources().getStringArray(R.array.phone_call_receive_options);
                }
                else {
                    optionsHints = new String[0];
                }

                for(int index = 0; index < optionsHints.length; index++) {
                    optionsLayouts.get(index).setHint(optionsHints[index]);
                    optionsLayouts.get(index).setEnabled(true);
                }

            }
        });

        return view;
    }
}
