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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddTaskBottomSheet extends BottomSheetDialogFragment {

    private TextInputEditText textInputEditText;
    private MaterialButton addButton;

    private AddTaskListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_task_bottomsheet,container, false);

        textInputEditText = v.findViewById(R.id.taskBottomSheetEditText);
        addButton = v.findViewById(R.id.addTaskBottomSheetButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskText = textInputEditText.getText().toString();

                if(!taskText.trim().isEmpty()){
                    listener.OnAddClick(taskText);
                    dismiss();
                }else {
                    Toast.makeText(getContext(), "Please Enter a Task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }


    public interface AddTaskListener{
        void OnAddClick(String taskText);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddTaskListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
            + " implement methods");
        }
    }
}
