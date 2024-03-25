package org.common.csv;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;


@RequiredArgsConstructor
@Slf4j
public class CsvWriter<T> {
    private final File file;
    private FileWriter writer;
    private final Function<T, String[]> parser;

    private void openWriter() {
        try {
            writer = new FileWriter(file, true);
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

    public void insertRow(T item) {
        openWriter();
        String[] cells = parser.apply(item);
        try {
            for (int i = 0; i < cells.length; i++) {
                writer.write(cells[i] + (i == cells.length - 1 ? "\n" : ", "));
            }
            writer.flush(); // Flush changes to file
            closeWriter();
        } catch (IOException e) {
            log.error("Failed to flush CSVWriter: " + e.getMessage());
        }
    }
}
