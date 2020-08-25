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
}
