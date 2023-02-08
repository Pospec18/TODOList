package com.example.todolist.data;

import java.util.ArrayList;
import java.util.List;

public class ItemListsHolder {
    private final List<String> listsFiles;
    private int lastUsedListIdx = 0;

    public ItemListsHolder(List<String> listsFiles, int lastUsedListIdx) {
        this.listsFiles = listsFiles;
        this.lastUsedListIdx = lastUsedListIdx;
    }

    public String getLastUsedList() {
        return  listsFiles.get(lastUsedListIdx);
    }
}
