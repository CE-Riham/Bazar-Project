package org.common.parsers;

import org.common.models.Book;

import java.util.function.Function;

public class BookParser {
    private BookParser() {
        throw new UnsupportedOperationException("Parser class should not be instantiated");
    }

    public static final Function<String[], Book> stringArrayToBook = cells -> {
        if (cells.length != 5) {
            throw new IllegalArgumentException("Invalid line format");
        }
        try {
            Book book = new Book();
            book.setId(cells[0].trim());
            book.setTitle(cells[1].trim());
            book.setPrice(Double.parseDouble(cells[2].trim()));
            book.setQuantity(Integer.parseInt(cells[3].trim()));
            book.setCategoryName(cells[4].trim());
            return book;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing number", e);
        }
    };

    public static final Function<Book, String[]> bookToStringArray = book -> {
        String[] row = new String[5];
        row[0] = book.getId().toString();
        row[1] = book.getTitle().toString();
        row[2] = book.getPrice().toString();
        row[3] = book.getQuantity().toString();
        row[4] = book.getCategoryName().toString();
        return row;
    };
}
