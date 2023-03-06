package com.example.todolist.data;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveAndLoad {
    private final static String listsPath = "lists";

    public static ItemHolder loadItems(String fileName, Context context) {
        try (ObjectInputStream oos = new ObjectInputStream(context.openFileInput(fileName))) {
            return (ItemHolder) oos.readObject();
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Item> itemsList = new ArrayList<>();
        itemsList.add(new Item());
        return new ItemHolder(itemsList, fileName, new Filter());
    }

    public static void saveItems(ItemHolder items, Context context) {
        try (ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(items.getFileName(), Context.MODE_PRIVATE))) {
            oos.writeObject(items);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
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

        List<ListNames> lists = new ArrayList<>();
        lists.add(new ListNames("General", "General"));
        return new ItemListsHolder(lists, 0, 0);
    }

    public static void saveLists(ItemListsHolder itemListsHolder, Context context) {
        try (ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(listsPath, Context.MODE_PRIVATE))) {
            oos.writeObject(itemListsHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String fileName, Context context) {
        context.deleteFile(fileName);
    }
}
