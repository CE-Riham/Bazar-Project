package org.catalog.services;

import org.common.enums.BookColumn;
import org.common.models.Book;
import org.common.parsers.BookParser;
import org.common.repository.Repository;

import java.util.List;
import java.util.UUID;

public class BookService {
    private static final String BOOKS_FILE_PATH = "catalog_service/data/Books.csv";
    private final Repository<Book> bookRepository;

    public BookService() {
        bookRepository = new Repository<>(BOOKS_FILE_PATH, new BookParser());
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    public Book getBookById(String id) {
        return bookRepository.getObjectBy(BookColumn.ID.toString(), id);
    }

    public void createBook(Book newBook) {
        newBook.setId(UUID.randomUUID().toString());
        bookRepository.add(newBook);
    }

    public void updateBookById(String id, Book newBook) {
        bookRepository.updateObjectsBy(BookColumn.ID.toString(), id, newBook);
    }

    public void deleteBookById(String id) {
        bookRepository.deleteObjectsBy(BookColumn.ID.toString(), id);
    }
}
