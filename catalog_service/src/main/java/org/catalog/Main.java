package org.catalog;

import lombok.extern.slf4j.Slf4j;
import org.catalog.controllers.BookController;
import org.catalog.controllers.CategoryController;
import org.catalog.services.BookService;
import org.catalog.services.CategoryService;

import static spark.Spark.*;

@Slf4j
public class Main {

    private static final String JSON_TYPE = "application/json";
    private static final Integer PORT = 8081;

    public static void main(String[] args) {
        log.info(String.format("Catalog server started on port %d", PORT));
        port(PORT);
        setupControllers();
        setupExceptionHandling();
        after((req, res) ->
                res.type(JSON_TYPE)
        );
    }

    private static void setupControllers() {
        CategoryService categoryService = new CategoryService();
        BookService bookService = new BookService();

        new CategoryController(categoryService);
        new BookController(bookService);
    }

    private static void setupExceptionHandling() {
        exception(Exception.class, (exception, request, response) -> {
            log.error("Unhandled exception", exception);
            response.status(500);
            response.body("Server error occurred: " + exception.getMessage());
        });
    }
}
