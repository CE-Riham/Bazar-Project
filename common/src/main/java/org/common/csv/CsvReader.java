package org.common.csv;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class CsvReader<T> {

    //    private final File file;
    private final String path;
    private Function<String[], T> parser;
    private static final String DELIMITER = ",";
    private static final String ERROR_MSG = "Failed to read orders from CSV: %s";

    public CsvReader(String path) {
        this.path = path;
    }

    public CsvReader(String path, Function<String[], T> parser) {
        this.path = path;
        this.parser = parser;
    }

    public String[] getHeader() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine().split(DELIMITER);
        } catch (IOException e) {
            log.error(String.format(ERROR_MSG, e.getMessage()));
        }
        return null;
    }

    public List<T> getAll() {
        List<T> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // Skip the header row
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(DELIMITER);
                T item = parser.apply(cells);
                items.add(item);
            }
        } catch (IOException e) {
            log.error(String.format(ERROR_MSG, e.getMessage()));
        }
        return items;
    }

    public List<String[]> getLinesWithCondition(String columnName, String value, Boolean equal) {
        List<String[]> lines = new ArrayList<>();
        int columnIndex = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String[] headers = br.readLine().split(DELIMITER);
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].trim().equals(columnName)) {
                    columnIndex = i;
                    break;
                }
            }
            if (columnIndex == -1)
                return lines;
            String row;
            while ((row = br.readLine()) != null) {
                String[] cells = row.split(DELIMITER);
                if (cells[columnIndex].equals(value) ^ Boolean.FALSE.equals(equal)) {
                    lines.add(cells);
                }
            }
        } catch (IOException e) {
            log.error(String.format(ERROR_MSG, e.getMessage()));
        }
        return lines;
    }

    public List<T> getObjectsWithCondition(String columnName, String value) {
        List<String[]> lines = getLinesWithCondition(columnName, value, true);
        List<T> items = new ArrayList<>();
        for (String[] cells : lines) {
            T item = parser.apply(cells);
            items.add(item);
        }
        return items;
    }

    public T getObjectWithCondition(String columnName, String value) {
        List<String[]> lines = getLinesWithCondition(columnName, value, true);
        if (lines.isEmpty()) return null;
        return parser.apply(lines.get(0));
    }
}