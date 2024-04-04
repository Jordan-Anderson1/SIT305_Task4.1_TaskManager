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

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    public long dateInMillis;



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

               //Create calendar instance
               Calendar calendar = Calendar.getInstance();

               calendar.set(Calendar.YEAR, year);
               calendar.set(Calendar.MONTH, month);
               calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

               dateInMillis = calendar.getTimeInMillis();
           }
       });


        //submit title, description and duedate on click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Ensure date is valid to avoid null pointer issues
                if(dateInMillis == 0){
                    Toast.makeText(AddActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
                    return;
                }

                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();

                //Ensure title and description are not empty or null
                if(title == null || title.equals("") || description == null || description.equals("")){
                    Toast.makeText(AddActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }



                //add data to DB
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addTodo(title, description, dateInMillis);

                //go back to main activity
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}