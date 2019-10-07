package com.blogspot.codecampanion.todoplanner.Room.Task;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM taskTable")
    void deleteAll();

    @Query("SELECT * FROM taskTable")
    LiveData<List<Task>> getTasks();
}
