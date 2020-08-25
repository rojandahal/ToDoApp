package com.project.todo.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.todo.util.ToDoRepository;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {

    private ToDoRepository toDoRepository;
    LiveData<List<ToDo>> allToDo;

    public ToDoViewModel(@NonNull Application application) {
        super(application);
        toDoRepository = new ToDoRepository(application);
        allToDo = toDoRepository.getAllToDoList();
    }

    /**
     *This method will get all the live data.
     * This method is repeated in database, repository and here
     * because we want to make every component independent so that the data are synchronized and
     * UI thread doesn't need to handle all the work load
     */
    public LiveData<List<ToDo>> getAllToDo(){
        return allToDo;
    }

    /**
     * This will insert the live data from the ui to the database
     */
    public void insert (ToDo toDo){
        toDoRepository.insert(toDo);
    }

}
