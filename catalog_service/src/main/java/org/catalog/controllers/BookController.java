package org.catalog.controllers;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.catalog.services.BookService;
import org.common.enums.StatusCode;
import org.common.enums.urls.BookUrl;
import org.common.enums.urls.CategoryUrl;
import org.common.models.Book;
import org.common.models.Category;
import org.common.utils.HttpRequestSender;
import org.common.utils.MessageResponse;

import static spark.Spark.*;

@Log
public class BookController {

    private final BookService bookService;
    private final Gson gson = new Gson();
    private static final String BOOK_ID_PARAMETER = BookUrl.BOOK_ID_PARAMETER.getUrl();
    private static final String CATEGORY_ID_PARAMETER = BookUrl.CATEGORY_ID_PARAMETER.getUrl();

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
            put(BookUrl.UPDATE_BOOKS_CATEGORY_PATH.getUrl(), this::updateBooksCategoryName, gson::toJson);
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
        Object checkResult = checkBookExists(bookId, res);
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

    private String buildCategoryUrl(String categoryId) {
        return CategoryUrl.CATALOG_SERVICE_BASE.getUrl() + CategoryUrl.CATEGORY_API_PATH.getUrl() + "/" + categoryId;
    }

    private Object fetchCategoryNameById(String categoryId, spark.Response res) {
        String getCategoryUrl = buildCategoryUrl(categoryId);
        // send GET request to catalog server
        String getCategoryResponse = HttpRequestSender.sendGetRequest(getCategoryUrl, res);

        if (res.status() == StatusCode.OK.getCode()) {
            return (gson.fromJson(getCategoryResponse, Category.class)).getName();
        } else {
            return gson.fromJson(getCategoryResponse, MessageResponse.class);
        }
    }

    private MessageResponse updateBooksCategoryName(spark.Request req, spark.Response res) {
        try {
            String newCategoryName = gson.fromJson(req.body(), Category.class).getName();
            // get oldCategoryName
            String oldCategoryID = req.params(CATEGORY_ID_PARAMETER);
            Object oldCategoryName = fetchCategoryNameById(oldCategoryID, res);
            if (res.status() == StatusCode.OK.getCode()) {
                bookService.updateBooksCategoryName((String) oldCategoryName, newCategoryName);
                res.status(StatusCode.OK.getCode());
            } else {
                // MessageResponse if category doesn't exist
                return (MessageResponse) oldCategoryName;
            }
            return new MessageResponse("Books Category was updated successfully.");
        } catch (Exception e) {
            res.status(StatusCode.NOT_FOUND.getCode());
            return new MessageResponse("Couldn't update books category.");
        }
    }

}