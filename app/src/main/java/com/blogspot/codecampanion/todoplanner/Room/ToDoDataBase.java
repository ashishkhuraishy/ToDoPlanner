package com.blogspot.codecampanion.todoplanner.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.blogspot.codecampanion.todoplanner.Room.SubTask.SubTask;
import com.blogspot.codecampanion.todoplanner.Room.SubTask.SubTaskDao;
import com.blogspot.codecampanion.todoplanner.Room.Task.Task;
import com.blogspot.codecampanion.todoplanner.Room.Task.TaskDao;

@Database(entities = {Task.class, SubTask.class}, version = 1)
abstract class ToDoDataBase extends RoomDatabase {

    private static ToDoDataBase instance;

    abstract TaskDao taskDao();
    abstract SubTaskDao subTaskDao();

    static synchronized ToDoDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ToDoDataBase.class, "ToDo_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }


}
