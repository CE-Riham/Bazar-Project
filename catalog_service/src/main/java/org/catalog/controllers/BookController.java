package org.catalog.controllers;

import com.google.gson.Gson;
import org.catalog.services.BookService;
import org.common.enums.StatusResponse;
import org.common.enums.urls.BookUrl;
import org.common.models.Book;
import org.common.utils.ApiResponse;

import static spark.Spark.*;

/**
 * This class is responsible for handling all the HTTP requests related to the Book entity.
 * It uses Spark framework to define and handle routes.
 */
public class BookController {

    private final BookService bookService;
    private final Gson gson = new Gson();
    private static final String BOOK_ID_PARAMETER = BookUrl.BOOK_ID_PARAMETER.getUrl();

    /**
     * Constructor for the BookController class.
     * @param bookService The service to be used for handling book related operations.
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
        setupRoutes();
    }

    /**
     * This method sets up the routes for handling HTTP requests.
     */
    private void setupRoutes() {
        path(BookUrl.BOOK_API_PATH.getUrl(), () -> {
            get(BookUrl.GET_ALL_BOOKS_PATH.getUrl(), this::getAllBooks, gson::toJson);
            get(BookUrl.GET_BOOK_BY_ID_PATH.getUrl(), this::getBookByTd, gson::toJson);
        });

        path(BookUrl.BOOK_ADMIN_API_PATH.getUrl(), () -> {
            post(BookUrl.CREATE_BOOK_PATH.getUrl(), this::createBook, gson::toJson);
            put(BookUrl.UPDATE_BOOK_BY_ID_PATH.getUrl(), this::updateBookById, gson::toJson);
            delete(BookUrl.DELETE_BOOK_BY_ID_PATH.getUrl(), this::deleteBookById, gson::toJson);
        });
    }

    /**
     * This method handles the GET request to fetch all books.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @return ApiResponse object containing the status and the list of all books.
     */
    private ApiResponse getAllBooks(spark.Request req, spark.Response res) {
        return new ApiResponse(StatusResponse.SUCCESS, bookService.getAllBooks());
    }

    /**
     * This method handles the GET request to fetch a book by its ID.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @return ApiResponse object containing the status and the requested book.
     */
    private ApiResponse getBookByTd(spark.Request req, spark.Response res) {
        String bookId = req.params(BOOK_ID_PARAMETER);
        return new ApiResponse(StatusResponse.SUCCESS, bookService.getBookById(bookId));
    }

    /**
     * This method handles the POST request to create a new book.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @return ApiResponse object containing the status of the operation.
     */
    private ApiResponse createBook(spark.Request req, spark.Response res) {
        Book newBook = gson.fromJson(req.body(), Book.class);
        bookService.createBook(newBook);
        return new ApiResponse(StatusResponse.SUCCESS);
    }

    /**
     * This method handles the PUT request to update a book by its ID.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @return ApiResponse object containing the status of the operation.
     */
    private ApiResponse updateBookById(spark.Request req, spark.Response res) {
        Book newBook = gson.fromJson(req.body(), Book.class);
        newBook.setId(req.params(BOOK_ID_PARAMETER));
        bookService.updateBookById(req.params(BOOK_ID_PARAMETER), newBook);
        return new ApiResponse(StatusResponse.SUCCESS);
    }

    /**
     * This method handles the DELETE request to delete a book by its ID.
     * @param req The HTTP request.
     * @param res The HTTP response.
     * @return ApiResponse object containing the status of the operation.
     */
    private ApiResponse deleteBookById(spark.Request req, spark.Response res) {
        String bookId = req.params(BOOK_ID_PARAMETER);
        bookService.deleteBookById(bookId);
        return new ApiResponse(StatusResponse.SUCCESS);
    }
}