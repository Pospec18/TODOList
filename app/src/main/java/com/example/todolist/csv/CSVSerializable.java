package com.example.todolist.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public interface CSVSerializable {
    public void toCSV(CSVWriter writer);
    public void fromCSV(CSVReader reader);
}
