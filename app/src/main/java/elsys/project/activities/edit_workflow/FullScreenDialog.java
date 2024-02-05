package elsys.project.activities.edit_workflow;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalTime;
import java.util.ArrayList;

import elsys.project.R;
import elsys.project.activities.edit_workflow.modules.BackgroundAlarmModule;
import elsys.project.activities.edit_workflow.modules.CallLogModule;
import elsys.project.activities.edit_workflow.modules.CallReceiverModule;
import elsys.project.activities.edit_workflow.modules.Module;
import elsys.project.activities.edit_workflow.modules.SendSmsModule;
import elsys.project.activities.edit_workflow.modules.SmsReceiverModule;
import elsys.project.activities.edit_workflow.modules.SoundAlarmModule;

public class FullScreenDialog extends DialogFragment {
    private FloatingActionButton createModule;
    private String pickedTitle;
    private String pickedSubhead;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.d("visible module", "1");
                //createModule.setVisibility(View.VISIBLE);
            }
        });

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        createModule = container.findViewById(R.id.create_module_button);
        createModule.setVisibility(View.INVISIBLE);
        RecyclerView recyclerView = container.findViewById(R.id.moduleRecycle);
        recyclerView.setVisibility(View.INVISIBLE);
        View view = inflater.inflate(R.layout.create_module_dialog, container, false);

        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTitleText("Pick timing before firing off")
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build();

        AutoCompleteTextView module_title = view.findViewById(R.id.moduleTitleMenu);
        AutoCompleteTextView module_subhead = view.findViewById(R.id.moduleSubheadMenu);

        Button buttonCancel = view.findViewById(R.id.cancelModuleButton);
        Button buttonSave = view.findViewById(R.id.saveModuleButton);
        Button buttonSelectTime = view.findViewById(R.id.selectTimeButton);
        buttonSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialTimePicker.show(requireActivity().getSupportFragmentManager(), "AlarmTime");
            }
        });

        ArrayList<TextInputLayout> optionsLayouts = new ArrayList<>(0);
        optionsLayouts.add(view.findViewById(R.id.moduleOption1Layout));
        optionsLayouts.add(view.findViewById(R.id.moduleOption2Layout));

        ArrayList<TextInputEditText> optionsInputs = new ArrayList<>(0);
        optionsInputs.add(view.findViewById(R.id.moduleOption1Input));
        optionsInputs.add(view.findViewById(R.id.moduleOption2Input));
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createModule.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                dismiss();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Module newModule = null;

                Log.d("module items", "1");

                if(pickedTitle.equals(Module.ALARM)) {
                    LocalTime timePickerValue = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        timePickerValue = LocalTime.of(materialTimePicker.getHour(), materialTimePicker.getMinute());
                    }
                    if(pickedSubhead.equals(Module.BACKGROUND_ALARM)) {
                        newModule = new BackgroundAlarmModule(timePickerValue);
                    }
                    else if(pickedSubhead.equals(Module.SOUND_ALARM)) {
                        newModule = new SoundAlarmModule(timePickerValue);
                    }
                    Log.d("module items", "2");
                }
                else if(pickedTitle.equals(Module.SMS)) {
                    if(pickedSubhead.equals(Module.SEND_SMS)) {
                        newModule = new SendSmsModule(optionsInputs.get(0).getText().toString(), optionsInputs.get(1).getText().toString());
                    }
                    else if(pickedSubhead.equals(Module.RECEIVE_SMS)) {
                        newModule = new SmsReceiverModule(optionsInputs.get(0).getText().toString(), optionsInputs.get(1).getText().toString());
                    }
                    Log.d("module items", "3");
                }
                else if(pickedTitle.equals(Module.PHONE_CALL)) {
                    if(pickedSubhead.equals(Module.RECEIVE_PHONE_CALL)) {
                        newModule = new CallLogModule();
                    }
                    Log.d("module items", "4");
                }
                EditWorkflowActivity.modules.add(newModule);
                EditWorkflowActivity.adapter.notifyDataSetChanged();
                Log.d("module items", "5");
                createModule.setVisibility(View.VISIBLE);
                Log.d("module items", "6");
                recyclerView.setVisibility(View.VISIBLE);
                Log.d("module items", "7");
                dismiss();
            }
        });

        module_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] items;
                ArrayAdapter<String> adapter = null;

                for(TextInputLayout optionLayout : optionsLayouts) {
                    optionLayout.setEnabled(false);
                    optionLayout.setHint("");
                }

                for(TextInputEditText optionInput : optionsInputs) {
                    optionInput.setText("");
                }

                pickedTitle = (String) adapterView.getItemAtPosition(i);

                if(pickedTitle.equals(Module.ALARM)) {
                    items = getResources().getStringArray(R.array.alarm_subheads);
                    adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, items);
                    for(TextInputLayout optionLayout : optionsLayouts) {
                        optionLayout.setVisibility(View.GONE);
                    }
                    buttonSelectTime.setVisibility(View.VISIBLE);

                }
                else if(pickedTitle.equals(Module.SMS)) {
                    items = getResources().getStringArray(R.array.SMS_subheads);
                    adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, items);
                    for(TextInputLayout optionLayout : optionsLayouts) {
                        optionLayout.setVisibility(View.VISIBLE);
                    }
                    buttonSelectTime.setVisibility(View.GONE);
                }
                else if(pickedTitle.equals(Module.PHONE_CALL)){
                    items = getResources().getStringArray(R.array.phone_call_subheads);
                    adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, items);
                    for(TextInputLayout optionLayout : optionsLayouts) {
                        optionLayout.setVisibility(View.VISIBLE);
                    }
                    buttonSelectTime.setVisibility(View.GONE);
                }
                module_subhead.setText("");
                module_subhead.setAdapter(adapter);
            }

        });

        module_subhead.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                pickedSubhead = (String) adapterView.getItemAtPosition(i);

                String[] optionsHints = null;

                if(pickedTitle.equals(Module.ALARM)) {
                    if(pickedSubhead.equals(Module.BACKGROUND_ALARM)) {
                        optionsHints = getResources().getStringArray(R.array.background_alarm_options);
                    }
                    else if(pickedSubhead.equals(Module.SOUND_ALARM)) {
                        optionsHints = getResources().getStringArray(R.array.sound_alarm_options);
                    }
                }
                else if(pickedTitle.equals(Module.SMS)) {
                    if(pickedSubhead.equals(Module.SEND_SMS)) {
                        optionsHints = getResources().getStringArray(R.array.SMS_send_options);
                    }
                    else if(pickedSubhead.equals(Module.RECEIVE_SMS)) {
                        optionsHints = getResources().getStringArray(R.array.SMS_receive_options);
                        optionsInputs.get(0).setInputType(InputType.TYPE_CLASS_PHONE);
                        optionsInputs.get(0).addTextChangedListener(new PhoneNumberFormattingTextWatcher());
                    }
                }
                else if(pickedTitle.equals(Module.PHONE_CALL)) {
                    if(pickedSubhead.equals(Module.RECEIVE_PHONE_CALL)) {
                        optionsHints = getResources().getStringArray(R.array.phone_call_log_options);
                    }
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
