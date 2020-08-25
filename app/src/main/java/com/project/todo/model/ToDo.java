package com.project.todo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class will create an entity of a table with a column and id for the data to store
 * in the table
 * In the ROOM database we use annotations to denote different works.
 * Like here the annotation entity tells that this class is an entity and with a table table as given below
 * Also for the integer annotation tells that it is primary key and can be auto generated
 * Same goes for the column
 */
@Entity(tableName = "todo_table")
public class ToDo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "todo_col")
    private String toDo;

    public ToDo(@NonNull String toDo) {
        this.toDo = toDo;
    }

    public String getToDo() {
        return toDo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToDo(@NonNull String toDo) {
        this.toDo = toDo;
    }
}
