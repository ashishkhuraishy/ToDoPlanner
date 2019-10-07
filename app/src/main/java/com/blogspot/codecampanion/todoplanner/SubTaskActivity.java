package com.blogspot.codecampanion.todoplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SubTaskActivity extends AppCompatActivity {

    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.round_close_black_24dp);
        setTitle("Edit Task");

        Intent intent = getIntent();
        taskId = intent.getIntExtra(MainActivity.EXTRA_TASK_ID, -1);
        String taskText = intent.getStringExtra(MainActivity.EXTRA_TASK);
    }
}
