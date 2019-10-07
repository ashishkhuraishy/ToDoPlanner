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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blogspot.codecampanion.todoplanner.Adapters.TaskAdapter;
import com.blogspot.codecampanion.todoplanner.Fragments.AddTaskBottomSheet;
import com.blogspot.codecampanion.todoplanner.Room.Task.Task;
import com.blogspot.codecampanion.todoplanner.Room.ToDoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddTaskBottomSheet.AddTaskListener {

    public static final String EXTRA_TASK_ID = "com.blogspot.codecampanion.todoplanner.EXTRA_TASK_ID";
    public static final String EXTRA_TASK = "com.blogspot.codecampanion.todoplanner.EXTRA_TASK";

    private static final int EDIT_TASK_ACTION = 9;

    private ToDoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton floatingActionButton = findViewById(R.id.addTaskFab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskBottomSheet addTaskBottomSheet = new AddTaskBottomSheet();
                addTaskBottomSheet.show(getSupportFragmentManager(), "Add Task BottomSheet");
            }
        });

        RecyclerView recyclerView = findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final TaskAdapter adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.submitList(tasks);
            }
        });

        adapter.setListener(new TaskAdapter.setOnItemClickListener() {
            @Override
            public void OnItemClick(Task task) {
                Intent intent = new Intent(MainActivity.this, SubTaskActivity.class);
                intent.putExtra(EXTRA_TASK_ID, task.getId());
                intent.putExtra(EXTRA_TASK, task.getTask());
                startActivityForResult(intent, EDIT_TASK_ACTION);

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
                viewModel.deleteTask(adapter.getTask(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void OnAddClick(String taskText) {
        viewModel.insertTask(new Task(taskText));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(getApplicationContext());
        inflater.inflate(R.menu.delete_all_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteAllTaskMenu:
                viewModel.deleteAllTask();
                Toast.makeText(this, "Deleted All!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
