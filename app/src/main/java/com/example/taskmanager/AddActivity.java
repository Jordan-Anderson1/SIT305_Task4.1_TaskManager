package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

       EditText titleInput = findViewById(R.id.titleInput);
       EditText descriptionInput = findViewById(R.id.descriptionInput);
       Button submitButton = findViewById(R.id.submitButton);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addTodo(titleInput.getText().toString(), descriptionInput.getText().toString());

                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}