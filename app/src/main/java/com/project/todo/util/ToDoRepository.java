package com.project.todo.util;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.project.todo.data.ToDoDao;
import com.project.todo.data.ToDoRoomDatabase;
import com.project.todo.model.ToDo;

import java.util.List;

/**
 * This repository will be used to handle the database. i.e the upper layer (ui and live data)
 * will contact the repository which will contact the database in background process to access the data
 * and handle the data
 */
public class ToDoRepository {

    private ToDoDao toDoDao;
    private LiveData<List<ToDo>> allToDoList;

    public ToDoRepository(Application application) {

        //We can also get data from remote api and add it in the list
        ToDoRoomDatabase db = ToDoRoomDatabase.getDatabase(application);
        toDoDao = db.toDoDao();
        allToDoList = toDoDao.getAllToDo();
    }

    public LiveData<List<ToDo>> getAllToDoList(){
        return allToDoList;
    }

    /**
     * This method will insert to the database using background thread i.e async task
     */
    public void insert(ToDo toDo){
        new insertAsyncTask(toDoDao).execute(toDo);
    }

    /**
     * This method will delete the database using background
     */
    public void delete(ToDo toDo){
        new deleteAsyncTask(toDoDao).execute(toDo);
    }

    /**
     * This method will be used to delete all the data in the database
     */
    public void deleteAll(){
        new deleteAllAsync(toDoDao).execute();
    }

    /**
     * This class will execute the insert method of the ToDoRoomDatabase in background thread
     * so that our UI thread is not affected
     */
    private class insertAsyncTask extends AsyncTask<ToDo, Void, Void> {

        private ToDoDao asyncTaskDao;
        public insertAsyncTask(ToDoDao Dao) {
            asyncTaskDao = Dao;
        }

        @Override
        protected Void doInBackground(ToDo... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * This class will delete a item from the database using the async task
     */
    private class deleteAsyncTask extends AsyncTask<ToDo, Void, Void> {

        private ToDoDao asyncDeleteDao;
        public deleteAsyncTask(ToDoDao toDoDao) {
            asyncDeleteDao = toDoDao;
        }

        @Override
        protected Void doInBackground(ToDo... toDos) {
            asyncDeleteDao.deleteAToDo(toDos[0]);
            return null;
        }
    }

    /**
     * This class will delete all the items in the list
     */
    private class deleteAllAsync extends AsyncTask<Void,Void,Void>{
        private ToDoDao deleteAllToDoDao;

        public deleteAllAsync(ToDoDao toDoDao) {
            deleteAllToDoDao = toDoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            toDoDao.deleteAll();
            return null;
        }
    }
}
