package com.example.taskmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    EditText updateTitleInput;
    EditText updateDescriptionInput;
    CalendarView updateCalendarInput;

    Intent intent;

    Button updateButton;

    String updatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        //manage intent
        intent = getIntent();
        Todo todo = (Todo) intent.getSerializableExtra("todo");


        //Initialise components
        updateTitleInput = findViewById(R.id.updateTitleInput);
        updateDescriptionInput = findViewById(R.id.updateDescriptionInput);
        updateCalendarInput = findViewById(R.id.updateDateInput);
        updateButton = findViewById(R.id.updateButton);


        //set values
        updateTitleInput.setText(todo.getTitle());
        updateDescriptionInput.setText(todo.getDescription());

        //get updated date value from calendar
        updateCalendarInput.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String formattedDay = String.format("%02d", dayOfMonth);
                updatedDate = year + "-" + (month + 1) + "-" + formattedDay;
            }
        });

        //UPDATE ACTIVITY ONCE SUBMIT IS CLICKED
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LOGIC TO UPDATE THE ACTIVITY

                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.updateData(String.valueOf(todo.getId()), updateTitleInput.getText().toString(), updateDescriptionInput.getText().toString(),
                        updatedDate);
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });






    }
}