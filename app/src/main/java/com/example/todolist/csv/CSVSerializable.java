package com.example.todolist.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.Reader;
import java.io.Writer;

public interface CSVSerializable {
    public void toCSV(Writer writer);
    public void fromCSV(Reader reader);
}
