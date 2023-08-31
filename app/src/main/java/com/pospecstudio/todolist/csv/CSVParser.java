package com.pospecstudio.todolist.csv;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public static <T> List<T> readCSV(Reader reader, Class<? extends T> c) throws IllegalStateException {
        CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
                .withType(c)
                .build();

        List<T> result = cb.parse();

        if (result == null)
            return new ArrayList<>();
        return result;
    }

    public static <T> void writeCSV(Writer writer, List<T> data) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder<T>(writer)
                .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();

        sbc.write(data);
    }
}
