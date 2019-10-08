package com.blogspot.codecampanion.todoplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.codecampanion.todoplanner.Adapters.SubTaskAdapter;
import com.blogspot.codecampanion.todoplanner.Fragments.AddSubTaskBottomSheet;
import com.blogspot.codecampanion.todoplanner.Room.SubTask.SubTask;
import com.blogspot.codecampanion.todoplanner.Room.ToDoViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SubTaskActivity extends AppCompatActivity implements AddSubTaskBottomSheet.AddSubTaskListener {
    public static final String EXTRA_SUB_TASK = "com.blogspot.codecampanion.todoplanner.EXTRA_SUB_TASK";
    public static final String EXTRA_SUB_TASK_ID = "com.blogspot.codecampanion.todoplanner.EXTRA_SUB_TASK_ID";

    private int taskId;
    private String taskText;

    private ToDoViewModel viewModel;
    private SubTaskAdapter adapter;

    private TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.round_close_black_24dp);
        setTitle("Edit Task");

        Intent intent = getIntent();
        taskId = intent.getIntExtra(MainActivity.EXTRA_TASK_ID, -1);
        taskText = intent.getStringExtra(MainActivity.EXTRA_TASK);

        textInputEditText = findViewById(R.id.subTaskEditText);
        MaterialButton button = findViewById(R.id.addSubTaskButton);

        textInputEditText.setText(taskText);

        textInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE){
                    String temp = textInputEditText.getText().toString();
                    if(!temp.trim().isEmpty()){
                        taskText = temp;
                    }else {
                        Toast.makeText(SubTaskActivity.this, "Task Cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSubTaskBottomSheet addSubTaskBottomSheet = new AddSubTaskBottomSheet();
                addSubTaskBottomSheet.show(getSupportFragmentManager(), "Add SubTask BottomSheet");
            }
        });

        RecyclerView recyclerView = findViewById(R.id.subTaskrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new SubTaskAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        viewModel.getSubTasks(taskId).observe(this, new Observer<List<SubTask>>() {
            @Override
            public void onChanged(List<SubTask> subTasks) {
                adapter.submitList(subTasks);
            }
        });

        adapter.setListner(new SubTaskAdapter.setOnItemClickListner() {
            @Override
            public void onItemClick(SubTask subTask) {
                AddSubTaskBottomSheet addSubTaskBottomSheet = new AddSubTaskBottomSheet();
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_SUB_TASK, subTask.getSubTask());
                bundle.putInt(EXTRA_SUB_TASK_ID, subTask.getId());
                addSubTaskBottomSheet.setArguments(bundle);
                addSubTaskBottomSheet.show(getSupportFragmentManager(), "Add SubTask BottomSheet");
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteSubTask(adapter.getSubTask(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveMenuButton:
                //stuff to do
                saveTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveTask() {

        taskText = textInputEditText.getText().toString();

        if(!taskText.trim().isEmpty()) {
            Intent data = new Intent();
            data.putExtra(MainActivity.EXTRA_TASK_ID, taskId);
            data.putExtra(MainActivity.EXTRA_TASK, taskText);
            setResult(RESULT_OK, data);
            finish();
        }else {
            Toast.makeText(this, "Task Cannot Be empty", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onAddClicked(String subTaskText, int subTaskId) {
        SubTask subTask = new SubTask(subTaskText, taskId);

        if(subTaskId != -1){
            subTask.setId(subTaskId);
            viewModel.updateSubTask(subTask);
        }else {
            viewModel.insertSubTask(subTask);
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
}
