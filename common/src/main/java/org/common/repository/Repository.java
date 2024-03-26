package org.common.repository;

import org.common.csv.CsvReader;
import org.common.csv.CsvWriter;
import org.common.parsers.ParserInterface;

import java.util.ArrayList;
import java.util.List;

public class Repository<T> {
    private final CsvReader csvReader;
    private final CsvWriter csvWriter;
    private final ParserInterface<T> parser;

    public Repository(String path, ParserInterface parser) {
        csvReader = new CsvReader(path);
        csvWriter = new CsvWriter(path);
        this.parser = parser;
    }

    private List<T> stringListToObjectList(List<String[]> lines) {
        List<T> items = new ArrayList<>();
        for (String[] line : lines)
            items.add(parser.toObject(line));
        return items;
    }

    public List<T> getAll() {
        return stringListToObjectList(csvReader.getAll());
    }

    public List<T> getObjectsBy(String columnName, String value) {
        return stringListToObjectList(csvReader.getLinesWithCondition(columnName, value, true));
    }

    public T getObjectBy(String columnName, String value) {
        List<String[]> lines = csvReader.getLinesWithCondition(columnName, value, true);
        if (lines.isEmpty()) return null;
        return parser.toObject(lines.get(0));
    }

    public void add(T object){
        csvWriter.insertLine(parser.toStringArray(object));
    }

    public void deleteObjectsBy(String columnName, String value){
        csvWriter.deleteObjectsWithCondition(columnName, value);
    }

    public void updateObjectsBy(String columnName, String value, String newValue){
        List<T> items = getAll();

    }
}
