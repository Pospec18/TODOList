package com.example.todolist.csv;

import java.io.Reader;
import java.io.Writer;

public interface CSVSerializable {
    void toCSV(Writer writer);
    void fromCSV(Reader reader);
}
