package com.example.todolist.data;

import java.io.Serializable;
import java.util.List;

public class ItemListsHolder implements Serializable {
    private final List<ListNames> listsData;
    private int lastUsedListIdx = 0;
    private int nextListID;

    public ItemListsHolder(List<ListNames> listsData, int lastUsedListIdx, int nextListID) {
        this.listsData = listsData;
        this.lastUsedListIdx = lastUsedListIdx;
        this.nextListID = nextListID;
    }

    public ListNames getLastUsedList() {
        if (listsData.size() < 1)
            return null;
        if (lastUsedListIdx > listsData.size())
            lastUsedListIdx = 0;
        return listsData.get(lastUsedListIdx);
    }

    public List<ListNames> getListsData() {
        return listsData;
    }

    public void setLastUsedListIdx(int lastUsedListIdx) {
        this.lastUsedListIdx = lastUsedListIdx;
    }

    public String getNextListName() {
        return "List" + nextListID++;
    }
}
