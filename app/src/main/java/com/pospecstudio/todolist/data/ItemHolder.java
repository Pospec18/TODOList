package com.pospecstudio.todolist.data;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.pospecstudio.todolist.csv.CSVParser;
import com.pospecstudio.todolist.csv.CSVSerializable;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.pospecstudio.todolist.helper.Collections;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemHolder implements Serializable, CSVSerializable, Printable {
    private List<Item> items;
    private final String fileName;
    private int editedItemIdx = -1;
    private Filter filter = new Filter();
    private final List<SortingType> sortingOrder = new ArrayList<>();

    private static final long serialVersionUID = 5480838046586935873L;

    public ItemHolder(List<Item> items, String fileName) {
        this.items = items;
        this.fileName = fileName;
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
    {
        aInputStream.defaultReadObject();
        if (filter == null)
            filter = new Filter();
    }

    public List<Item> getFilteredItems() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return filterItems();
        } else {
            return new ArrayList<>(items);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Item> filterItems(Predicate<? super Item> predicate) {
        return items.stream()
                .filter(this::canShowItem)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Item> filterItems() {
        if (sortingOrder == null || sortingOrder.size() == 0)
            return items.stream()
                    .filter(this::canShowItem)
                    .collect(Collectors.toList());

        return items.stream()
                .filter(this::canShowItem)
                .sorted(this::sort)
                .collect(Collectors.toList());
    }

    private boolean canShowItem(Item item) {
        if (isEditedItem(item))
            return true;
        return filter.canShow(item);
    }

    private int sort(Item a, Item b) {
        for (SortingType comparator : sortingOrder) {
            int result = comparator.compare(a, b);
            if (result != 0)
                return result;
        }

        return indexOf(a) - indexOf(b);
    }

    public void addItem(Item item) {
        editedItemIdx = items.size();
        items.add(item);
    }

    public void deleteItem(Item item) {
        items.remove(item);
    }

    public int indexOf(Item item) {
        return items.indexOf(item);
    }

    public Item getItemToEdit(int index) {
        editedItemIdx = index;
        return items.get(index);
    }

    public String getFileName() {
        return fileName;
    }

    public Filter getFilter() {
        return filter;
    }

    public void forgetIndexOfEditedItem() {
        editedItemIdx = -1;
    }

    public boolean isEditedItem(Item item) {
        return indexOf(item) == editedItemIdx;
    }

    public void moveAboveItem(Item itemToMove, Item itemToStay) {
        if (itemToStay == null) {
            moveItemToEnd(itemToMove);
            return;
        }

        int from = items.indexOf(itemToMove);
        int to = items.indexOf(itemToStay);
        if (from < to)
            to--;

        if (from >= 0 && to >= 0)
            Collections.move(items, from, to);
    }

    public void moveItemToEnd(Item item) {
        items.remove(item);
        items.add(item);
    }

    @Override
    public void toCSV(Writer writer) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        CSVParser.writeCSV(writer, items);
    }

    @Override
    public void fromCSV(Reader reader) throws IllegalStateException {
         items = CSVParser.readCSV(reader, Item.class);
    }

    public void print(StringBuilder builder) {
        for (Item i: items) {
            i.print(builder);
            builder.append('\n');
        }
    }
}
