package com.pospecstudio.todolist.data;

import java.io.Serializable;
import java.util.Comparator;

public class SortingType implements Serializable {
    private final String title;
    private boolean isAscending = true;
    private final Comparator<? super Item> comparator;

    public SortingType(String title, Comparator<? super Item> comparator) {
        this.title = title;
        this.comparator = comparator;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAscending() {
        return isAscending;
    }

    public void setAscending(boolean ascending) {
        isAscending = ascending;
    }

    public int compare(Item a, Item b) {
        int sign = isAscending ? 1 : -1;
        return sign * comparator.compare(a, b);
    }
}
