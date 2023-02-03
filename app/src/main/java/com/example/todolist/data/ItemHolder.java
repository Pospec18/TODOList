package com.example.todolist.data;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemHolder {
    private List<Item> items;

    public ItemHolder(List<Item> items) {
        this.items = items;
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
}
