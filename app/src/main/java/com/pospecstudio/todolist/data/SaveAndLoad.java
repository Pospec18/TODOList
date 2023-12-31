package com.pospecstudio.todolist.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveAndLoad {
    private final static String listsPath = "lists";
    private static final String TAG = "List IO";

    public static ItemHolder loadItems(String fileName, Context context) {
        try (ObjectInputStream oos = new ObjectInputStream(context.openFileInput(fileName))) {
            return (ItemHolder) oos.readObject();
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            Log.e(TAG, "Failed to load items", e);
        }
        ArrayList<Item> itemsList = new ArrayList<>();
        itemsList.add(new Item());
        return new ItemHolder(itemsList, fileName);
    }

    public static void saveItems(ItemHolder items, Context context) {
        try (ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(items.getFileName(), Context.MODE_PRIVATE))) {
            oos.writeObject(items);
        } catch (IOException e) {
            Log.e(TAG, "Unable to read file: " + items.getFileName(), e);
        } catch (Exception e) {
            Log.e(TAG, "Failed to save items", e);
        }
    }

    public static ItemListsHolder loadLists(Context context) {
        try (ObjectInputStream oos = new ObjectInputStream(context.openFileInput(listsPath))) {
            return (ItemListsHolder) oos.readObject();
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            Log.e(TAG, "Failed to load lists", e);
        }

        List<ListNames> lists = new ArrayList<>();
        lists.add(new ListNames("General", "General"));
        return new ItemListsHolder(lists, 0, 0);
    }

    public static void saveLists(ItemListsHolder itemListsHolder, Context context) {
        try (ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput(listsPath, Context.MODE_PRIVATE))) {
            oos.writeObject(itemListsHolder);
        } catch (IOException e) {
            Log.e(TAG, "Unable to read file: " + listsPath, e);
        }
    }

    public static void deleteFile(String fileName, Context context) {
        try {
            context.deleteFile(fileName);
        } catch (Exception e) {
            Log.e(TAG, "Deleting file: " + fileName, e);
        }
    }

    public static void exportListToCSV(ContentResolver resolver, Uri uri, ItemHolder holder) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        OutputStream os = resolver.openOutputStream(uri);
        try (Writer writer = new OutputStreamWriter(os)) {
            holder.toCSV(writer);
        }
    }

    public static void importListFromCSV(ContentResolver resolver, Uri uri, ItemHolder holder) throws IOException, IllegalStateException {
        InputStream is = resolver.openInputStream(uri);
        try (Reader reader = new InputStreamReader(is)) {
            holder.fromCSV(reader);
        }
    }
}
