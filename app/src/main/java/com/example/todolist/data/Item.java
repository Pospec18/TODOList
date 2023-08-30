package com.example.todolist.data;

import android.os.Build;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Item implements Serializable {
    @CsvBindByName(column = "name", required = true)
    private String itemName;
    @CsvBindByName(column = "idealCount", required = true)
    private int idealCount;
    @CsvBindByName(column = "currCount")
    private int currCount;
    @CsvIgnore
    private boolean hide = false;
    @CsvBindByName(column = "optional")
    private boolean optional = false;

    @CsvBindByName(column = "createdTime")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime createdTime = null;
    @CsvBindByName(column = "changedTime")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime changedTime = null;
    @CsvBindByName(column = "numberOfChanges")
    private int numberOfChanges;
    @CsvBindByName(column = "type")
    private ItemType type;

    private static final long serialVersionUID = 1714191732972138209L;

    public Item() {
        itemName = "edit item for new name";
        idealCount = 1;
        currCount = 0;
        numberOfChanges = 0;
        type = ItemType.Number;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createdTime = ZonedDateTime.now();
            changedTime = ZonedDateTime.now();
        }
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

    public boolean isHide() {
        return hide;
    }

    public boolean isOptional() {
        return optional;
    }

    public ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    public ZonedDateTime getChangedTime() {
        return changedTime;
    }

    public FilledType getFilledType() {
        if (currCount >= idealCount)
            return FilledType.FULLY;
        if (optional)
            return FilledType.OPTIONAL;
        if (currCount > 0)
            return FilledType.PARTIALLY;
        return FilledType.EMPTY;
    }

    public int getNumberOfChanges() {
        return numberOfChanges;
    }
    public ItemType getType() {
        return type;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName.trim();
        onChanged();
    }

    public void setIdealCount(int idealCount) {
        this.idealCount = idealCount;
        onChanged();
    }

    public void setCurrCount(int currCount) {
        this.currCount = currCount;
        onChanged();
    }

    public void changeCurrCountBy(int offset) {
        this.currCount += offset;
        onChanged();
    }

    public void increaseCurrCount() {
        currCount++;
        onChanged();
    }

    public void decreaseCurrCount() {
        currCount--;
        onChanged();
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    private void onChanged()
    {
        numberOfChanges++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            changedTime = ZonedDateTime.now();
        }
    }
}
