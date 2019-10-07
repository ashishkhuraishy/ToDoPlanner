package com.blogspot.codecampanion.todoplanner.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.blogspot.codecampanion.todoplanner.Room.SubTask.SubTask;
import com.blogspot.codecampanion.todoplanner.Room.SubTask.SubTaskDao;
import com.blogspot.codecampanion.todoplanner.Room.Task.Task;
import com.blogspot.codecampanion.todoplanner.Room.Task.TaskDao;

import java.util.List;

class ToDoRepository {

    private TaskDao taskDao;
    private SubTaskDao subTaskDao;

    ToDoRepository(Application application){

        ToDoDataBase toDoDataBase = ToDoDataBase.getInstance(application);
        taskDao = toDoDataBase.taskDao();
        subTaskDao = toDoDataBase.subTaskDao();

    }

    void insertTask(Task task){
        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    void updateTask(Task task){
        new UpdateTaskAsyncTask(taskDao).execute(task);
    }

    void deleteTask(Task task){
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }

    void deleteAllTask(){
        new DeleteAllTaskAsyncTask(taskDao).execute();
    }

    LiveData<List<Task>> getTasks(){
        return taskDao.getTasks();
    }

    void insertSubTask(SubTask subTask){
        new InsertSubTaskAsyncTask(subTaskDao).execute(subTask);
    }

    void updateSubTask(SubTask subTask){
        new UpdateSubTaskAsyncTask(subTaskDao).execute(subTask);
    }

    void deleteSubTask(SubTask subTask){
        new DeleteSubTaskAsyncTask(subTaskDao).execute(subTask);
    }

    LiveData<List<SubTask>> getSubTasks(int taskId){
        return subTaskDao.getSubTasks(taskId);
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        UpdateTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;

        DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }

    private static class DeleteAllTaskAsyncTask extends AsyncTask<Void, Void, Void>{

        private TaskDao taskDao;

        DeleteAllTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAll();
            return null;
        }
    }


    private static class InsertSubTaskAsyncTask extends AsyncTask<SubTask, Void, Void>{

        private SubTaskDao subTaskDao;

        InsertSubTaskAsyncTask(SubTaskDao subTaskDao) {
            this.subTaskDao = subTaskDao;
        }

        @Override
        protected Void doInBackground(SubTask... subTasks) {
            subTaskDao.insert(subTasks[0]);
            return null;
        }
    }

    private static class UpdateSubTaskAsyncTask extends AsyncTask<SubTask, Void, Void>{

        private SubTaskDao subTaskDao;

        UpdateSubTaskAsyncTask(SubTaskDao subTaskDao) {
            this.subTaskDao = subTaskDao;
        }

        @Override
        protected Void doInBackground(SubTask... subTasks) {
            subTaskDao.update(subTasks[0]);
            return null;
        }
    }

    private static class DeleteSubTaskAsyncTask extends AsyncTask<SubTask, Void, Void>{

        private SubTaskDao subTaskDao;

        DeleteSubTaskAsyncTask(SubTaskDao subTaskDao) {
            this.subTaskDao = subTaskDao;
        }

        @Override
        protected Void doInBackground(SubTask... subTasks) {
            subTaskDao.delete(subTasks[0]);
            return null;
        }
    }


}
