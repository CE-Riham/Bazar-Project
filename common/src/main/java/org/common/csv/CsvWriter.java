package org.common.csv;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;


@RequiredArgsConstructor
@Slf4j
public class CsvWriter<T> {
    //    private final File file;
    private FileWriter writer;
    private final String path;
    private final Function<T, String[]> parser;

    private void openWriter(Boolean append) {
        try {
            writer = new FileWriter(new File(path), append);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void closeWriter() {
        try {
            writer.close(); // Close the writer to ensure all data is written
        } catch (IOException e) {
            log.error("Failed to close CSVWriter: " + e.getMessage());
        }
    }

    public void insertLine(String[] item) {
        openWriter(true);
        try {
            for (int i = 0; i < item.length; i++) {
                writer.write(item[i] + (i == item.length - 1 ? "\n" : ", "));
            }
            writer.flush(); // Flush changes to file
            closeWriter();
        } catch (IOException e) {
            log.error("Failed to flush CSVWriter: " + e.getMessage());
        }
    }

    public void insertObject(T item) {
        openWriter(true);
        insertLine(parser.apply(item));
    }

    public void clearFile() {
        String[] header = new CsvReader<>(path).getHeader();
        openWriter(false);
        closeWriter();
        insertLine(header);
    }

    public void deleteObjectsWithCondition(String columnName, String value) {
        // Read all lines from the CSV file into a list where columnName not equal value
        List<String[]> lines = new CsvReader<T>(path).getLinesWithCondition(columnName, value, false);

        // Clear the file to write the remaining rows back
        clearFile();

        for (String[] line : lines)
            insertLine(line);

    }
}
