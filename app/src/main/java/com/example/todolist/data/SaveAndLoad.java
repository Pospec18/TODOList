package com.example.todolist.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import com.example.todolist.csv.CSVParser;

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
        return new ItemHolder(itemsList, fileName);
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

    public static void exportListToCSV(ItemHolder holder, String nameInDownladsFolder) {
        File downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        try (Writer writer = new FileWriter(new File(downloads, nameInDownladsFolder + ".csv"))) {
            holder.toCSV(writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importListFromCSV(ContentResolver resolver, Uri uri, ItemHolder holder)
    {
        InputStream is;
        try {
             is = resolver.openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Reader reader = new InputStreamReader(is)) {
            holder.fromCSV(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
