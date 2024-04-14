package org.catalog.services;

import org.common.enums.columns.BookColumn;
import org.common.models.Book;
import org.common.parsers.BookParser;
import org.common.repository.Repository;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class BookService {
    private static final String BOOKS_FILE_PATH = "catalog_service/data/Books.csv";
    private final Repository<Book> bookRepository;

    public BookService() {
        File booksFile = null;

        try {
            booksFile = Paths.get("data", "Books.csv").toFile();

            if (!booksFile.exists() || booksFile.isDirectory()) {
                throw new Exception("Default file path is not valid.");
            }
        } catch (Exception e) {
            booksFile = new File(BOOKS_FILE_PATH);
        }

        bookRepository = new Repository<>(booksFile, new BookParser());
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    public Book getBookById(String id) {
        return bookRepository.getObjectBy(BookColumn.ID.toString(), id);
    }

    public Book createBook(Book newBook) {
        newBook.setId(UUID.randomUUID().toString());
        bookRepository.add(newBook);
        return newBook;
    }

    public Book updateBookById(String id, Book newBook) {
        bookRepository.updateObjectsBy(BookColumn.ID.toString(), id, newBook);
        return getBookById(id);
    }

    public void deleteBookById(String id) {
        bookRepository.deleteObjectsBy(BookColumn.ID.toString(), id);
    }

    public void updateBooksCategoryName(String oldCategoryName, String newCategoryName) throws Exception {
        bookRepository.updateSpecificField(BookColumn.CATEGORY.toString(), oldCategoryName,
                BookColumn.CATEGORY.toString(), newCategoryName);
    }

    public List<Book> getBooksByName(String title) {
        return bookRepository.getObjectsContainsBy(BookColumn.TITLE.toString(), title);
    }
}
