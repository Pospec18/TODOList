package com.example.todolist.data;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.todolist.csv.CSVParser;
import com.example.todolist.csv.CSVSerializable;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemHolder implements Serializable, CSVSerializable {
    private List<Item> items;
    private final String fileName;
    public static final Filter filter = new Filter();
    private static final long serialVersionUID = 5480838046586935873L;

    public ItemHolder(List<Item> items, String fileName) {
        this.items = items;
        this.fileName = fileName;
    }

    public List<Item> filterNoItems() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return filterItems(item -> true);
        } else {
            return items;
        }
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
    public void toCSV(Writer writer) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        CSVParser.writeCSV(writer, items);
    }

    @Override
    public void fromCSV(Reader reader) throws IllegalStateException {
         items = CSVParser.readCSV(reader, Item.class);
    }
}
