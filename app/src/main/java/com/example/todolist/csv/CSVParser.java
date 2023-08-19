package com.example.todolist.csv;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public static <T> List<T> readCSV(Reader reader, Class<? extends T> c) {
        CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
                .withType(c)
                .build();

        List<T> result = cb.parse();
        if (result == null)
            return new ArrayList<>();
        return result;
    }

    public static <T> void writeCSV(Writer writer, List<T> data) {
        try {
            StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            sbc.write(data);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }
}