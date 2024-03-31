package org.common.parsers;

import org.common.models.Book;

/**
 * This class implements the ParserInterface for the Book entity.
 * It provides methods to convert a Book object to a string array and vice versa.
 */
public class BookParser implements ParserInterface<Book> {

    /**
     * This method converts a string array to a Book object.
     * It expects the string array to have exactly 5 elements (ID, title, price, quantity, category name).
     * If the string array does not have exactly 5 elements, it throws an IllegalArgumentException.
     * If the price or quantity cannot be parsed to a number, it throws an IllegalArgumentException.
     *
     * @param line The string array to be converted to a Book object.
     * @return The Book object.
     */
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

    /**
     * This method converts a Book object to a string array.
     * The string array will have exactly 5 elements (ID, title, price, quantity, category name).
     *
     * @param object The Book object to be converted to a string array.
     * @return The string array.
     */
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