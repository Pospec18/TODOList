package com.example.todolist.data;

import java.util.ArrayList;
import java.util.List;

public class SaveAndLoad {
    public static List<Item> loadItems() {
        List<Item> result = new ArrayList<>();
        result.add(new Item());
        result.add(new Item("test2", 2, 1));
        return result;
    }

    public static void saveItems(List<Item> items) {

    }

    public static List<Item> loadPrevItems() {
        return new ArrayList<>();
    }
}
