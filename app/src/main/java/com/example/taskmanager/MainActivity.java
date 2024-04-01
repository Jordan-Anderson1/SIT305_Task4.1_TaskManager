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
    ArrayList<Boolean> todo_completed;


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

    //swipe to delete functionality
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch(direction) {
                case ItemTouchHelper.LEFT:
                    todosList.remove(position);
                    recyclerViewAdapter.notifyItemRemoved(position);

                    ////ADD LOGIC TO REMOVE FROM DB
                    break;
                case ItemTouchHelper.RIGHT:

                    break;
            }
        }

        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.icons8_delete)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }


    };
}