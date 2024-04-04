package com.example.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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





        holder.todo_title_text.setText(todosList.get(position).getTitle());
        holder.todo_description_text.setText(todosList.get(position).getDescription());
        holder.todo_id_text.setText(String.valueOf(todosList.get(position).getId()));
        holder.todo_date_text.setText(String.valueOf(todosList.get(position).getDueDate()));









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
