package org.catalog.services;

import org.common.csv.CsvReader;
import org.common.csv.CsvWriter;
import org.common.models.Book;
import org.common.parsers.BookParser;
import org.common.repository.Repository;

import java.util.List;
import java.util.UUID;

public class BookService {
    private static final String BOOKS_FILE_PATH = "catalog_service/data/Books.csv";

//    private final CsvReader<Book> bookCsvReader;
    private final Repository<Book> bookRepository;
//    private final CsvWriter<Book> bookCsvWriter;

    public BookService() {
//        File booksFile = Paths.get("data", "Books.csv").toFile();
        bookRepository = new Repository<>(BOOKS_FILE_PATH, new BookParser());
//        bookCsvReader = new CsvReader<>(BOOKS_FILE_PATH, BookParser.stringArrayToBook);
//        bookCsvWriter = new CsvWriter<>(BOOKS_FILE_PATH, BookParser.bookToStringArray);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    public Book getBookById(String id) {
//        return bookCsvReader.getObjectWithCondition("ID", id);
        return null;
    }

    public void createBook(Book newBook) {
        newBook.setId(UUID.randomUUID().toString());
//        bookCsvWriter.insertObject(newBook);
    }

    public void updateBookById(String id) {
        //TODO
    }

    public void deleteBookById(String id) {
//        bookCsvWriter.deleteObjectsWithCondition("ID", id);

    }
}
