package com.blogspot.codecampanion.todoplanner.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.blogspot.codecampanion.todoplanner.Room.SubTask.SubTask;
import com.blogspot.codecampanion.todoplanner.Room.Task.Task;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {

    private ToDoRepository toDoRepository;

    public ToDoViewModel(@NonNull Application application) {
        super(application);
        toDoRepository = new ToDoRepository(application);
    }


    void insertTask(Task task){
        toDoRepository.insertTask(task);
    }

    void updateTask(Task task){
        toDoRepository.updateTask(task);
    }

    void deleteTask(Task task){
        toDoRepository.deleteTask(task);
    }

    void deleteAllTask(){
        toDoRepository.deleteAllTask();
    }

    LiveData<List<Task>> getTasks(){
        return toDoRepository.getTasks();
    }

    void insertSubTask(SubTask subTask){
        toDoRepository.insertSubTask(subTask);
    }

    void updateSubTask(SubTask subTask){
        toDoRepository.updateSubTask(subTask);
    }

    void deleteSubTask(SubTask subTask){
        toDoRepository.deleteSubTask(subTask);
    }

    LiveData<List<SubTask>> getSubTasks(int taskId){
        return toDoRepository.getSubTasks(taskId);
    }


}
