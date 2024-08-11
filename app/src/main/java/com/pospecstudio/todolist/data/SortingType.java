package com.pospecstudio.todolist.data;

import java.util.Comparator;

public class SortingType {
    private final String title;
    private boolean isAscending;
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
        return comparator.compare(a, b);
    }
}
