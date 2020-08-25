package com.project.todo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.todo.model.ToDo;

import java.util.List;

/**
 * This class is a database access object interface so it just tells that some functions are to be done
 * but doesn't tell how the function is to be done
 * This class is used to access the database and do all sorts of CRUD operations
 * This will interact with the entity to create, delete, query or update the database
 */
@Dao
public interface ToDoDao {

    @Insert
    void insert(ToDo toDo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("DELETE FROM todo_table WHERE id = :id")
    int deleteAToDo(int id);

    @Query("UPDATE todo_table SET todo_col = :todoText WHERE id = :id")
    int updateToDoItem(String todoText, int id);

    @Query("SELECT * FROM todo_table ORDER BY todo_col DESC")
    LiveData<List<ToDo>> getAllToDo();

}
