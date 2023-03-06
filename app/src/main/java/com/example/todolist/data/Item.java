package com.example.todolist.data;

import com.example.todolist.csv.CSVSerializable;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.Serializable;

public class Item implements Serializable, CSVSerializable {
    private String itemName;
    private int idealCount;
    private int currCount;
    private boolean skipForNow = false;

    private static final long serialVersionUID = 1714191732972138209L;

    public Item() {
        itemName = "name";
        idealCount = 1;
        currCount = 0;
    }

    public Item(String itemName, int idealCount, int currCount) {
        this.itemName = itemName.trim();
        this.idealCount = idealCount;
        this.currCount = currCount;
    }

    public String getItemName() {
        return itemName;
    }

    public int getIdealCount() {
        return idealCount;
    }

    public int getCurrCount() {
        return currCount;
    }

    public boolean isSkipForNow() {
        return skipForNow;
    }

    public FilledType getFilledType() {
        if (idealCount == 0)
            return FilledType.FULLY;
        if (currCount < 1)
            return FilledType.EMPTY;
        if (idealCount - currCount > 0)
            return FilledType.PARTIALLY;
        else
            return FilledType.FULLY;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName.trim();
    }

    public void setIdealCount(int idealCount) {
        this.idealCount = idealCount;
    }

    public void setCurrCount(int currCount) {
        this.currCount = currCount;
    }

    public void increaseCurrCount() {
        currCount++;
    }

    public void decreaseCurrCount() {
        currCount--;
    }

    public void setSkipForNow(boolean skipForNow) {
        this.skipForNow = skipForNow;
    }

    @Override
    public void toCSV(CSVWriter writer) {
        writer.writeNext(new String[] { itemName, Integer.toString(idealCount), Integer.toString(currCount) });
    }

    @Override
    public void fromCSV(CSVReader reader) {

    }
}
