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

public class UpdateActivity extends AppCompatActivity {

    EditText updateTitleInput;
    EditText updateDescriptionInput;
    CalendarView updateCalendarInput;

    Intent intent;

    Button updateButton;

    String updatedDate;
    Long dateInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        //manage intent
        intent = getIntent();
        Todo todo = (Todo) intent.getSerializableExtra("todo"); //Gets todo object from intent


        //Initialise components
        updateTitleInput = findViewById(R.id.updateTitleInput);
        updateDescriptionInput = findViewById(R.id.updateDescriptionInput);
        updateCalendarInput = findViewById(R.id.updateDateInput);
        updateButton = findViewById(R.id.updateButton);



        //set values
        updateTitleInput.setText(todo.getTitle());
        updateDescriptionInput.setText(todo.getDescription());
        updateCalendarInput.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            //get updated date value from calendar
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




        //UPDATE ACTIVITY ONCE SUBMIT IS CLICKED
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LOGIC TO UPDATE THE ACTIVITY

                //Ensure date is valid to avoid null pointer exception
                if(dateInMillis == null || dateInMillis == 0){
                    Toast.makeText(UpdateActivity.this, "Please select new date", Toast.LENGTH_SHORT).show();
                    return;
                }

                String newTitle = String.valueOf(updateTitleInput.getText().toString());
                String newDescription = String.valueOf(updateDescriptionInput.getText().toString());

                //Ensure title and description are not null or empty
                if(newTitle == null || newTitle.equals("") || newDescription == null || newDescription.equals("")){
                    Toast.makeText(UpdateActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.updateData(String.valueOf(todo.getId()), newTitle,
                        newDescription, dateInMillis);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });






    }
}