package com.blogspot.codecampanion.todoplanner.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blogspot.codecampanion.todoplanner.R;
import com.blogspot.codecampanion.todoplanner.SubTaskActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddSubTaskBottomSheet extends BottomSheetDialogFragment {

    private AddSubTaskListener listener;

    private String subTaskText;
    private int subTaskId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_subtask_bottomsheet, container, false);
        final TextInputEditText textInputEditText = v.findViewById(R.id.subTaskBottomSheetEditText);
        MaterialButton addSubTaskButton = v.findViewById(R.id.addSubTaskBottomSheetButton);

        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(SubTaskActivity.EXTRA_SUB_TASK_ID)){
            subTaskId = bundle.getInt(SubTaskActivity.EXTRA_SUB_TASK_ID, -1);
            subTaskText = bundle.getString(SubTaskActivity.EXTRA_SUB_TASK);

            addSubTaskButton.setText("Update SubTask");

            textInputEditText.setText(subTaskText);
        }

        addSubTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subTask = textInputEditText.getText().toString();

                if(!subTask.trim().isEmpty()){
                    listener.onAddClicked(subTask, subTaskId);
                    dismiss();
                }else {
                    Toast.makeText(getContext(), "SubTask Cannot Be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }


    public interface AddSubTaskListener{
        void onAddClicked(String subTask, int subTaskId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddSubTaskListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
            + " implement methods!");
        }

    }
}
