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


    public void insertTask(Task task){
        toDoRepository.insertTask(task);
    }

    public void updateTask(Task task){
        toDoRepository.updateTask(task);
    }

    public void deleteTask(Task task){
        toDoRepository.deleteTask(task);
    }

    public void deleteAllTask(){
        toDoRepository.deleteAllTask();
    }

    public LiveData<List<Task>> getTasks(){
        return toDoRepository.getTasks();
    }

    public void insertSubTask(SubTask subTask){
        toDoRepository.insertSubTask(subTask);
    }

    public void updateSubTask(SubTask subTask){
        toDoRepository.updateSubTask(subTask);
    }

    public void deleteSubTask(SubTask subTask){
        toDoRepository.deleteSubTask(subTask);
    }

    public LiveData<List<SubTask>> getSubTasks(int taskId){
        return toDoRepository.getSubTasks(taskId);
    }


}
