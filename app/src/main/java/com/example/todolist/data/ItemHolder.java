package com.example.todolist.data;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.todolist.csv.CSVSerializable;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemHolder implements Serializable, CSVSerializable {
    private final List<Item> items;
    private final String fileName;
    public static final Filter filter = new Filter();
    private static final long serialVersionUID = 5480838046586935873L;

    public ItemHolder(List<Item> items, String fileName) {
        this.items = items;
        this.fileName = fileName;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Item> filterNoItems() {
        return filterItems(item -> true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Item> filterItems(Predicate<? super Item> predicate) {
        return items.stream()
                .filter(filter::canShow)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void deleteItem(Item item) {
        items.remove(item);
    }

    public int indexOf(Item item) {
        return items.indexOf(item);
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public void toCSV(CSVWriter writer) {
        writer.writeNext(new String[] {"itemName", "idealCount", "currCount"});
        for (Item item : items)
            item.toCSV(writer);
    }

    @Override
    public void fromCSV(CSVReader reader) {
        try {
            reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                String itemName = line[0];
                int idealCount = Integer.parseInt(line[1]);
                int currentCount = Integer.parseInt(line[2]);
                addItem(new Item(itemName, idealCount, currentCount));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
