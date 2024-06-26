package com.example.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Todo> todosList;
    private Context context;

    public RecyclerViewAdapter(List<Todo> todosList, Context context) {
        this.todosList = todosList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.my_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        //get instance of todo
        Todo todo = todosList.get(position);

        //get dueDate of todo in millis
        long dateInMillis = todo.getDueDate();

        //create date object and convert to calendar object
        Date date = new Date(dateInMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //extract data from calendar object
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        //convert to string
        String formattedDate = String.format("%02d-%02d-%04d", day, month, year);


        holder.todo_title_text.setText(todosList.get(position).getTitle());
        holder.todo_description_text.setText(todosList.get(position).getDescription());
        holder.todo_id_text.setText(String.valueOf(todosList.get(position).getId()));
        holder.todo_date_text.setText(formattedDate);

    }

    @Override
    public int getItemCount() {
        return todosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView todo_title_text;
        TextView todo_description_text;
        TextView todo_id_text;

        TextView todo_date_text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todo_title_text = itemView.findViewById(R.id.todo_title_text);
            todo_description_text = itemView.findViewById(R.id.todo_description_text);
            todo_id_text = itemView.findViewById(R.id.todo_id_text);
            todo_date_text = itemView.findViewById(R.id.todo_date_text);

        }
    }
}
