package org.catalog;

import org.catalog.controllers.BookController;
import org.catalog.controllers.CategoryController;
import org.catalog.services.BookService;
import org.catalog.services.CategoryService;
import static spark.Spark.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    static final private Integer PORT = 8081;
    public static void main(String[] args) {
        port(PORT);
        setupControllers();
        setupExceptionHandling();
        logger.info("Server started on port {}", PORT);
    }

    private static void setupControllers() {
        CategoryService categoryService = new CategoryService();
        BookService bookService = new BookService();

        new CategoryController(categoryService);
        new BookController(bookService);
    }

    private static void setupExceptionHandling() {
        exception(Exception.class, (exception, request, response) -> {
            logger.error("Unhandled exception", exception);
            response.status(500); // HTTP 500 Internal Server Error
            response.body("Server error occurred: " + exception.getMessage());
        });
    }
}
