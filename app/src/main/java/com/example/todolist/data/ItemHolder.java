package com.example.todolist.data;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemHolder implements Serializable {
    private final List<Item> items;
    private String listName;
    private transient String fileName;

    public ItemHolder(List<Item> items, String listName, String fileName) {
        this.items = items;
        this.listName = listName;
        this.fileName = fileName;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Item> filterNoItems() {
        return filterItems(item -> true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Item> filterItems(Predicate<? super Item> predicate) {
        return items.stream()
                .filter(item -> !item.isSkipForNow())
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void deleteItem(Item item) {
        items.remove(item);
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

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
