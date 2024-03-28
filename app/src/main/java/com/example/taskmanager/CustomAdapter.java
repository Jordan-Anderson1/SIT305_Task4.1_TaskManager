package com.example.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;

    private ArrayList todo_id, todo_title, todo_description;



    CustomAdapter(Context context,
                  ArrayList todo_id,
                  ArrayList todo_title,
                  ArrayList todo_description){
        this.context = context;
        this.todo_id = todo_id;
        this.todo_title = todo_title;
        this.todo_description = todo_description;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.todo_id_text.setText(String.valueOf(todo_id.get(position)));
        holder.todo_title_text.setText(String.valueOf(todo_title.get(position)));
        holder.todo_description_text.setText(String.valueOf(todo_description.get(position)));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView todo_id_text, todo_title_text, todo_description_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            todo_id_text = itemView.findViewById(R.id.todo_id_text);
            todo_title_text = itemView.findViewById(R.id.todo_title_text);
            todo_description_text = itemView.findViewById(R.id.todo_description_text);

        }
    }
}
