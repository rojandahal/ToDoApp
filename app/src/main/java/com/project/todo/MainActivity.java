package com.project.todo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.project.todo.model.ToDo;
import com.project.todo.model.ToDoViewModel;
import com.project.todo.ui.ToDoListAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int THIS_CODE_REQUEST = 1;
    private ToDoListAdapter toDoListAdapter;
    private ToDoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        todoViewModel = ViewModelProviders.of(this)
                .get(ToDoViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        toDoListAdapter = new ToDoListAdapter(this);
        recyclerView.setAdapter(toDoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddToDoActivity.class);
                startActivityForResult(intent,THIS_CODE_REQUEST);
            }
        });

        //This will get all the ToDos and will observe the change and this will track the change and
        //propagate everywhere
        todoViewModel.getAllToDo().observe(this, new Observer<List<ToDo>>() {
            @Override
            public void onChanged(List<ToDo> toDos) {
                //Update the cached copy of ToDOs in the adapter
                toDoListAdapter.setToDO(toDos);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == THIS_CODE_REQUEST && resultCode == RESULT_OK){
            assert data!=null;
            ToDo toDo = new ToDo(data.getStringExtra(AddToDoActivity.EXTRA_STRING));
            todoViewModel.insert(toDo);
        }else
            Toast.makeText(this, R.string.not_saved, Toast.LENGTH_LONG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}