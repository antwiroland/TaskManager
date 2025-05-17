package com.example.taskManager.model;

import java.util.Date;

public class Task {
    private int id;
    private String name;
    private String status;
    private Date dueDate;

    public Task(int id, String name, String status, Date dueDate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.dueDate = dueDate;
    }

    public Task(String name, String status, Date dueDate) {
        this.name = name;
        this.status = status;
        this.dueDate = dueDate;
    }

    public Task() {
        // default constructor
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
