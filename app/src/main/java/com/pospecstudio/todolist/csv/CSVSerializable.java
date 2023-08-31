package com.pospecstudio.todolist.csv;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.Reader;
import java.io.Writer;

public interface CSVSerializable {
    void toCSV(Writer writer) throws IllegalStateException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    void fromCSV(Reader reader) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}
