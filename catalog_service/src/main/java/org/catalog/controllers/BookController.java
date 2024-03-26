package org.catalog.controllers;

import com.google.gson.Gson;
import org.catalog.services.BookService;
import org.common.models.Book;

import static spark.Spark.*;


public class BookController {

    private final BookService bookService;
    private final Gson gson = new Gson();
    private static final String BOOK_ID_PATH = "/:book-id";
    private static final String BOOK_ID_PARAMETER = ":book-id";

    public BookController(BookService bookService) {
        this.bookService = bookService;
        setupRoutes();
    }

    private void setupRoutes() {
        path("/api/book", () -> {
            get("", this::getAllBooks, gson::toJson);
            get(BOOK_ID_PATH, this::getBookByTd, gson::toJson);
        });

        path("/api/admin/book", () -> {
            post("", this::createBook);
            put(BOOK_ID_PATH, this::updateBookById);
            delete(BOOK_ID_PATH, this::deleteBookById);
        });
    }

    private Object getAllBooks(spark.Request req, spark.Response res) {
        return bookService.getAllBooks();
    }

    private Object getBookByTd(spark.Request req, spark.Response res) {
        String bookId = req.params(BOOK_ID_PARAMETER);
        return bookService.getBookById(bookId);
    }

    private String createBook(spark.Request req, spark.Response res) {
        Book newBook = gson.fromJson(req.body(), Book.class);
        bookService.createBook(newBook);
        return "Book was created successfully";
    }

    private String updateBookById(spark.Request req, spark.Response res) {
        Book newBook = gson.fromJson(req.body(), Book.class);
        newBook.setId(req.params(BOOK_ID_PARAMETER));
        bookService.updateBookById(req.params(BOOK_ID_PARAMETER),newBook);
        return "Book was updated successfully";
    }

    private String deleteBookById(spark.Request req, spark.Response res) {
        String bookId = req.params(BOOK_ID_PARAMETER);
        bookService.deleteBookById(bookId);
        return "Book was deleted successfully";
    }
}
