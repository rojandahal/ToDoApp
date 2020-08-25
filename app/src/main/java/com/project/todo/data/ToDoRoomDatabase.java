package com.project.todo.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.project.todo.model.ToDo;

/**
 * This abstract class is a database class where the database is created
 * this database class will connect the database access object and the entity
 * This class will have only one instance so it is abstract and this will create only one database
 * this will handle DAO which will access the entity
 */
@Database(entities = {ToDo.class}, version = 1)
public abstract class ToDoRoomDatabase extends RoomDatabase {

    //This is a volatile instance so that it is removed when memory is cleared
    public static volatile ToDoRoomDatabase DB;

    public abstract ToDoDao toDoDao();

    /**
     * This method will return the object of this same class
     * i.e it will return the ToDoRoomDatabase object if it is not created then it will
     * create and return so that we don't need to create object over and over again
     */
    public static ToDoRoomDatabase getDatabase(final Context context){
        if(DB == null){
            synchronized (ToDoRoomDatabase.class){
                if(DB == null){
                    //Create database
                    DB = Room.databaseBuilder(context.getApplicationContext(),
                            ToDoRoomDatabase.class,
                            "todo_database"
                            ).addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return DB;
    }

    /**
     * This callback method is called to set the callback and to set the database and build the database
     */
    private static RoomDatabase.Callback roomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(DB).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
        private final ToDoDao toDoDao;

        public PopulateDbAsync(ToDoRoomDatabase instance) {
            toDoDao = instance.toDoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//                toDoDao.deleteAll();    //Deletes all from table
            //For testing
//            toDoDao.insert(new ToDo("A big house"));
//            toDoDao.insert(new ToDo("A car"));
//            toDoDao.insert(new ToDo("An App"));

            return null;
        }
    }
}
