package com.pospecstudio.todolist.data;

import com.pospecstudio.todolist.helper.Collections;
import com.pospecstudio.todolist.helper.Numeric;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemListsHolder implements Serializable {
    private final List<ListNames> listsData;
    private int lastUsedListIdx = 0;
    private int nextListID;
    private static final long serialVersionUID = 6728568169479137671L;

    public ItemListsHolder(List<ListNames> listsData, int lastUsedListIdx, int nextListID) {
        this.listsData = listsData;
        this.lastUsedListIdx = lastUsedListIdx;
        this.nextListID = nextListID;
    }

    public ListNames getLastUsedList() {
        if (listsData.size() < 1)
            return null;
        if (lastUsedListIdx >= listsData.size() || lastUsedListIdx < 0)
            lastUsedListIdx = 0;
        return listsData.get(lastUsedListIdx);
    }

    public List<ListNames> getFilteredLists() {
        return new ArrayList<>(listsData);
    }

    public void setLastUsedList(ListNames list) {
        int index = listsData.indexOf(list);
        if (index >= 0)
            lastUsedListIdx = index;
    }

    public String getNextListName() {
        return "List" + nextListID++;
    }

    public void addList(ListNames list) {
        listsData.add(list);
    }

    public void removeList(ListNames list) {
        listsData.remove(list);
    }

    public int indexOf(ListNames list) {
        return listsData.indexOf(list);
    }

    public ListNames getListToEdit(int index) {
        return listsData.get(index);
    }

    public void moveAboveItem(ListNames itemToMove, ListNames itemToStay) {
        if (itemToStay == null) {
            moveItemToEnd(itemToMove);
            return;
        }

        int from = listsData.indexOf(itemToMove);
        int to = listsData.indexOf(itemToStay);
        if (from < to)
            to--;

        if (from >= 0 && to >= 0) {

            Collections.move(listsData, from, to);
        }

        if (lastUsedListIdx == from) {
            lastUsedListIdx = to;
        }
        else if (Numeric.isBetweenOrEqual(lastUsedListIdx, from, to)) {
            if (from < to)
                lastUsedListIdx--;
            else
                lastUsedListIdx++;
        }
    }

    public void moveItemToEnd(ListNames item) {
        int index = listsData.indexOf(item);
        if (index < 0)
            return;
        listsData.remove(index);
        listsData.add(item);
        if (lastUsedListIdx == index)
            lastUsedListIdx = listsData.size() - 1;
        else if (lastUsedListIdx > index)
            lastUsedListIdx--;
    }
}
