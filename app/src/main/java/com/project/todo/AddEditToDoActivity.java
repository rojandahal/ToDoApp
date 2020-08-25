package com.project.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditToDoActivity extends AppCompatActivity {

    public static final String EXTRA_STRING = "TODO_TEXT";
    private EditText todoText;
    private Button saveButton;
    private String textToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        todoText = findViewById(R.id.add_todo);
        saveButton = findViewById(R.id.buttonSave);
        textToDisplay = todoText.getText().toString();

        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.editing))){
            setTitle("Edit ToDo");
            textToDisplay = intent.getStringExtra(EXTRA_STRING);
            todoText.setText(textToDisplay);
        }else
            setTitle("Add ToDo");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                if(TextUtils.isEmpty(todoText.getText().toString())){
                    Toast.makeText(AddEditToDoActivity.this,"Please enter note",Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED, replyIntent);
                }else {
                    replyIntent.putExtra(EXTRA_STRING,todoText.getText().toString());
                    setResult(RESULT_OK,replyIntent);
                }
                finish();
            }
        });
    }
}