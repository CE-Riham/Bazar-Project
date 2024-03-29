package org.catalog.controllers;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.catalog.services.BookService;
import org.common.enums.StatusCode;
import org.common.enums.urls.BookUrl;
import org.common.models.Book;
import org.common.utils.MessageResponse;

import static spark.Spark.*;

@Log
public class BookController {

    private final BookService bookService;
    private final Gson gson = new Gson();
    private static final String BOOK_ID_PARAMETER = BookUrl.BOOK_ID_PARAMETER.getUrl();

    public BookController(BookService bookService) {
        this.bookService = bookService;
        setupRoutes();
    }

    private void setupRoutes() {
        path(BookUrl.BOOK_API_PATH.getUrl(), () -> {
            get(BookUrl.GET_ALL_BOOKS_PATH.getUrl(), this::getAllBooks, gson::toJson);
            get(BookUrl.GET_BOOK_BY_ID_PATH.getUrl(), this::getBookById, gson::toJson);
        });

        path(BookUrl.BOOK_ADMIN_API_PATH.getUrl(), () -> {
            post(BookUrl.CREATE_BOOK_PATH.getUrl(), this::createBook, gson::toJson);
            put(BookUrl.UPDATE_BOOK_BY_ID_PATH.getUrl(), this::updateBookById, gson::toJson);
            delete(BookUrl.DELETE_BOOK_BY_ID_PATH.getUrl(), this::deleteBookById, gson::toJson);
        });
    }

    private Object checkBookExists(String bookId, spark.Response res) {
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            res.status(StatusCode.OK.getCode());
            return book;
        } else {
            res.status(StatusCode.NOT_FOUND.getCode());
            return String.format("Book with id: %s is not found", bookId);
        }
    }

    private Object getAllBooks(spark.Request req, spark.Response res) {
        log.info("get all books method");
        res.status(StatusCode.OK.getCode());
        return bookService.getAllBooks();
    }

    private Object getBookById(spark.Request req, spark.Response res) {
        log.info("get book by id method");
        String bookId = req.params(BOOK_ID_PARAMETER);
        return checkBookExists(bookId, res);
    }

    private Object createBook(spark.Request req, spark.Response res) {
        log.info("create new book method");
        Book newBook = gson.fromJson(req.body(), Book.class);
        Book book = bookService.createBook(newBook);
        res.status(StatusCode.CREATED.getCode());
        return book;
    }

    private Object updateBookById(spark.Request req, spark.Response res) {
        log.info("update book method");
        String bookId = req.params(BOOK_ID_PARAMETER);
        Object checkResult =  checkBookExists(bookId, res);
        if (res.status() == 200) {
            Book newBook = gson.fromJson(req.body(), Book.class);
            newBook.setId(bookId);
            return bookService.updateBookById(bookId, newBook);
        }
        return new MessageResponse((String) checkResult);
    }

    private Object deleteBookById(spark.Request req, spark.Response res) {
        log.info("delete book method");
        String bookId = req.params(BOOK_ID_PARAMETER);
        Object checkResult = checkBookExists(bookId, res);
        if (res.status() == 200) {
            bookService.deleteBookById(bookId);
            return new MessageResponse("Book was deleted successfully.");
        }
        return new MessageResponse((String) checkResult);
    }


}