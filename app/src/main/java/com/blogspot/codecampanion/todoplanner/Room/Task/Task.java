package com.blogspot.codecampanion.todoplanner.Room.Task;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "taskTable")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String task;

    public Task(String task) {
        this.task = task;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }
}
