package org.common.csv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class CsvReader<T> {

    private final File file;
    private final Function<String[], T> parser;
    private static final String DELIMITER = ",";
    private static final String ERROR_MSG = "Failed to read orders from CSV: %s";

    public List<T> getAll() {
        List<T> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
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

    public List<T> getRows(String columnName, String value) {
        List<T> items = new ArrayList<>();
        int columnIndex = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String[] headers = br.readLine().split(DELIMITER);
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].trim().equals(columnName)) {
                    columnIndex = i;
                    break;
                }
            }
            if (columnIndex == -1)
                return items;
            String row;
            while ((row = br.readLine()) != null) {
                String[] cells = row.split(DELIMITER);
                if (cells[columnIndex].equals(value)) {
                    T item = parser.apply(cells);
                    items.add(item);
                }
            }
        } catch (IOException e) {
            log.error(String.format(ERROR_MSG, e.getMessage()));
        }
        return items;
    }

    public T getRow(String columnName, String value) {
        int columnIndex = -1;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String[] headers = br.readLine().split(DELIMITER);
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equals(columnName)) {
                    columnIndex = i;
                    break;
                }
            }
            if (columnIndex == -1)
                return null;
            String row;
            while ((row = br.readLine()) != null) {
                String[] cells = row.split(DELIMITER);
                if (cells[columnIndex].equals(value)) {
                    return parser.apply(cells);
                }
            }
        } catch (IOException e) {
            log.error(String.format(ERROR_MSG, e.getMessage()));
        }
        return null;
    }
}