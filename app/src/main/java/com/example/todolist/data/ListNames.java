package com.example.todolist.data;

import java.io.Serializable;

public class ListNames implements Serializable {
    private String listName;
    private final String fileName;

    public ListNames(String listName, String fileName) {
        this.listName = listName;
        this.fileName = fileName;
    }

    public String getListName() {
        return listName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
