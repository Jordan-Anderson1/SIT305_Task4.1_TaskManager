package com.example.taskmanager;

import java.io.Serializable;

public class Todo implements Serializable {

    private int id;
    private String title;
    private String description;

    private long dueDate;

    public Todo(int id, String title, String description, long dueDate){
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public long getDueDate() { return dueDate; }






}
