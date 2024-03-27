package org.catalog.controllers;

import com.google.gson.Gson;
import org.catalog.services.BookService;
import org.common.enums.BookServiceUrl;
import org.common.enums.StatusResponse;
import org.common.models.ApiResponse;
import org.common.models.Book;

import static spark.Spark.*;


public class BookController {

    private final BookService bookService;
    private final Gson gson = new Gson();
    private static final String BOOK_ID_PARAMETER = BookServiceUrl.BOOK_ID_PARAMETER.getUrl();

    public BookController(BookService bookService) {
        this.bookService = bookService;
        setupRoutes();
    }

    private void setupRoutes() {
        path(BookServiceUrl.BOOK_API_PATH.getUrl(), () -> {
            get(BookServiceUrl.GET_ALL_BOOKS_PATH.getUrl(), this::getAllBooks, gson::toJson);
            get(BookServiceUrl.GET_BOOK_BY_ID_PATH.getUrl(), this::getBookByTd, gson::toJson);
        });

        path(BookServiceUrl.BOOK_ADMIN_API_PATH.getUrl(), () -> {
            post(BookServiceUrl.CREATE_BOOK_PATH.getUrl(), this::createBook, gson::toJson);
            put(BookServiceUrl.UPDATE_BOOK_BY_ID_PATH.getUrl(), this::updateBookById, gson::toJson);
            delete(BookServiceUrl.DELETE_BOOK_BY_ID_PATH.getUrl(), this::deleteBookById, gson::toJson);
        });
    }

    private ApiResponse getAllBooks(spark.Request req, spark.Response res) {
        return new ApiResponse(StatusResponse.SUCCESS, bookService.getAllBooks());
    }

    private ApiResponse getBookByTd(spark.Request req, spark.Response res) {
        String bookId = req.params(BOOK_ID_PARAMETER);
        return new ApiResponse(StatusResponse.SUCCESS, bookService.getBookById(bookId));
    }

    private ApiResponse createBook(spark.Request req, spark.Response res) {
        Book newBook = gson.fromJson(req.body(), Book.class);
        bookService.createBook(newBook);
        return new ApiResponse(StatusResponse.SUCCESS);
    }

    private ApiResponse updateBookById(spark.Request req, spark.Response res) {
        Book newBook = gson.fromJson(req.body(), Book.class);
        newBook.setId(req.params(BOOK_ID_PARAMETER));
        bookService.updateBookById(req.params(BOOK_ID_PARAMETER), newBook);
        return new ApiResponse(StatusResponse.SUCCESS);
    }

    private ApiResponse deleteBookById(spark.Request req, spark.Response res) {
        String bookId = req.params(BOOK_ID_PARAMETER);
        bookService.deleteBookById(bookId);
        return new ApiResponse(StatusResponse.SUCCESS);
    }
}
