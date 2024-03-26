package org.common.parsers;

import org.common.models.Book;

import java.util.function.Function;

public class BookParser implements ParserInterface<Book>{

    @Override
    public Book toObject(String[] line) {
        if (line.length != 5) {
            throw new IllegalArgumentException("Invalid line format");
        }
        try {
            Book book = new Book();
            book.setId(line[0].trim());
            book.setTitle(line[1].trim());
            book.setPrice(Double.parseDouble(line[2].trim()));
            book.setQuantity(Integer.parseInt(line[3].trim()));
            book.setCategoryName(line[4].trim());
            return book;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing number", e);
        }
    }

    @Override
    public String[] toStringArray(Book object) {
        String[] row = new String[5];
        row[0] = object.getId();
        row[1] = object.getTitle();
        row[2] = object.getPrice().toString();
        row[3] = object.getQuantity().toString();
        row[4] = object.getCategoryName();
        return row;
    }
}
