package com.example.todolist.data;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveAndLoad {
    private final static String listsPath = "lists";

    public static ItemHolder loadItems(String filePath, Context context) {
        try (ObjectInputStream oos = new ObjectInputStream(context.openFileInput(filePath))) {
            ItemHolder items = (ItemHolder) oos.readObject();
            items.setFileName(filePath);
            return items;
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Item> itemsList = new ArrayList<>();
        itemsList.add(new Item());
        return new ItemHolder(itemsList, "New List", filePath);
    }

    public static void saveItems(ItemHolder items, Context context) {
        try (ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(items.getFileName(), Context.MODE_PRIVATE))) {
            oos.writeObject(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ItemListsHolder loadLists(Context context) {
        try (ObjectInputStream oos = new ObjectInputStream(context.openFileInput(listsPath))) {
            return (ItemListsHolder) oos.readObject();
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<String> fileNames = new ArrayList<>();
        fileNames.add("General");
        return new ItemListsHolder(fileNames, 0);
    }

    public static void saveLists(ItemListsHolder itemListsHolder, Context context) {
        try (ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(listsPath, Context.MODE_PRIVATE))) {
            oos.writeObject(itemListsHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
