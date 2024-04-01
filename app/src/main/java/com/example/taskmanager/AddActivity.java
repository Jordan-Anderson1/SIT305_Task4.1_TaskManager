package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    public String dateString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

       EditText titleInput = findViewById(R.id.titleInput);
       EditText descriptionInput = findViewById(R.id.descriptionInput);
       Button submitButton = findViewById(R.id.updateButton);

       CalendarView dateInput = findViewById(R.id.dateInput);

       //gets the date input and stores in dateString variable
       dateInput.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


           @Override
           public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

               String formattedDay = String.format("%02d", dayOfMonth);
               dateString = year + "-" + (month + 1) + "-" + formattedDay;
           }
       });


        //submit title, description and duedate on click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //add data to DB
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addTodo(titleInput.getText().toString(), descriptionInput.getText().toString(), dateString);

                //go back to main activity
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}