package com.example.todolist.data;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveAndLoad {
    private final static String filePath = "list";
    public static List<Item> loadItems(Context context) {
        try (ObjectInputStream oos = new ObjectInputStream(context.openFileInput(filePath))) {
            return (List<Item>)oos.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("file not found, creating new one");
            saveItems(new ArrayList<>(), context);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Item> a = new ArrayList<>();
        a.add(new Item());
        return a;
    }

    public static void saveItems(List<Item> items, Context context) {
        try (ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(filePath, Context.MODE_PRIVATE))) {
            oos.writeObject(items);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
