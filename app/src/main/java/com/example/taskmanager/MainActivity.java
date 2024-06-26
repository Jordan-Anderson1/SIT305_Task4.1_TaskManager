package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

//import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    RecyclerViewAdapter recyclerViewAdapter;
    List<Todo> todosList = new ArrayList<>();
    FloatingActionButton addButton;

    MyDatabaseHelper myDB;
    ArrayList<String> todo_id, todo_title, todo_description;
    ArrayList<Long> todo_dueDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(todosList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);

        itemTouchHelper.attachToRecyclerView(recyclerView);

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
        todo_dueDate = new ArrayList<>();



        //retrieve data
        storeDataInArrays();


        //takes data from arrays and creates instances of the Todo class and adds to list
        for(int i = 0; i < todo_id.size(); i++){
            Todo todo = new Todo(Integer.valueOf(todo_id.get(i)), todo_title.get(i), todo_description.get(i), todo_dueDate.get(i));
            todosList.add(todo);
        }

        sortByDate(todosList);




    }



    //Sorts list by date
    public void sortByDate(List<Todo> todos) {
        Collections.sort(todos, new Comparator<Todo>() {
            @Override
            public int compare(Todo o1, Todo o2) {
                return Long.compare(o1.getDueDate(), o2.getDueDate());
            }
        });
    }


        //retrieved data from DB and stores in cursor object. Then takes data from cursor and stores in
        //individual arrays.
        public void storeDataInArrays () {
            Cursor cursor = myDB.readAllData();
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    todo_id.add(cursor.getString(0));
                    todo_title.add(cursor.getString(1));
                    todo_description.add(cursor.getString(2));
                    todo_dueDate.add(cursor.getLong(3));
                }
            }
        }

        //recyclerView swipe logic
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                Todo todoToDelete = todosList.get(position);

                Todo todoToUpdate = todosList.get(position);

                //switch case for future functionality such as right swipe to edit or archive
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        // LOGIC TO REMOVE FROM DB
                        MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                        myDB.deleteById(todoToDelete.getId());
                        //logic to remove from recyclerview
                        todosList.remove(position);
                        recyclerViewAdapter.notifyItemRemoved(position);
                        break;
                    case ItemTouchHelper.RIGHT:
                        //Takes to edit screen
                        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                        intent.putExtra("todo", todoToUpdate);
                        startActivity(intent);

                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.deleteRed))
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.descriptionColor))
                        .addSwipeLeftActionIcon(R.drawable.icons8_delete)
                        .addSwipeRightActionIcon(R.drawable.icons8_edit)
                        .addPadding(1, 5, 3.5f, 5)
                        .addCornerRadius(5, 5)
                        .create()
                        .decorate();


                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


        };
    }
