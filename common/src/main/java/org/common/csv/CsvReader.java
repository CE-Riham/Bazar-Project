package org.common.csv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CsvReader{

    //    private final File file;
    private final String path;
    private static final String DELIMITER = ",";
    private static final String ERROR_MSG = "Failed to read orders from CSV: %s";

    public String[] getHeaders() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine().split(DELIMITER);
        } catch (IOException e) {
            log.error(String.format(ERROR_MSG, e.getMessage()));
        }
        return new String[0];
    }

    public int getHeaderIndex(String columnName){
        String[] headers = getHeaders();
        int index = -1;
        for (int i = 0; i < headers.length; i++)
            if (headers[i].equals(columnName)) {
                index = i;
                break;
            }
        return index;
    }

    public List<String[]> getAll() {
        List<String[]> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // Skip the header row
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(DELIMITER);
                lines.add(cells);
            }
        } catch (IOException e) {
            log.error(String.format(ERROR_MSG, e.getMessage()));
        }
        return lines;
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
                if (cells[columnIndex].trim().equals(value) ^ Boolean.FALSE.equals(equal)) {
                    lines.add(cells);
                }
            }
        } catch (IOException e) {
            log.error(String.format(ERROR_MSG, e.getMessage()));
        }
        return lines;
    }
}