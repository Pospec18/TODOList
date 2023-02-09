package com.example.todolist.data;

import java.io.Serializable;
import java.util.List;

public class ItemListsHolder implements Serializable {
    private final List<String> listsFiles;
    private int lastUsedListIdx = 0;

    public ItemListsHolder(List<String> listsFiles, int lastUsedListIdx) {
        this.listsFiles = listsFiles;
        this.lastUsedListIdx = lastUsedListIdx;
    }

    public String getLastUsedList() {
        return  listsFiles.get(lastUsedListIdx);
    }

    public List<String> getListsFiles() {
        return listsFiles;
    }

    public void setLastUsedListIdx(int lastUsedListIdx) {
        this.lastUsedListIdx = lastUsedListIdx;
    }
}
