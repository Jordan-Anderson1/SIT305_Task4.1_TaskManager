package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    RecyclerViewAdapter recyclerViewAdapter;
    List<Todo> todosList = new ArrayList<>();
    FloatingActionButton addButton;

    MyDatabaseHelper myDB;
    ArrayList<String> todo_id, todo_title, todo_description;
    ArrayList<Boolean> todo_completed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(todosList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addButton = findViewById(R.id.button);

        //Event listener for add button. Takes user to new activity to add a task.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        todo_id = new ArrayList<>();
        todo_title = new ArrayList<>();
        todo_description = new ArrayList<>();
        todo_completed = new ArrayList<>();


        //retrieve data
        storeDataInArrays();

        for(int i = 0; i < todo_id.size(); i++){
            Todo todo = new Todo(i, todo_title.get(i), todo_description.get(i));
            todosList.add(todo);
        }





    }


    //retrieved data from DB and stores in cursor object. Then taskes data from cursor and stores in
    //individual arrays.
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                todo_id.add(cursor.getString(0));
                todo_title.add(cursor.getString(1));
                todo_description.add(cursor.getString(2));

                //get integer value, convert to boolean. Add bool value to list.
                boolean completed = cursor.getInt(3) == 1;
                todo_completed.add(completed);
            }
        }
    }
}