package org.catalog;

import lombok.extern.slf4j.Slf4j;
import org.catalog.controllers.BookController;
import org.catalog.controllers.CategoryController;
import org.catalog.services.BookService;
import org.catalog.services.CategoryService;
import org.common.enums.Ports;

import static spark.Spark.*;

/**
 * This is the main class for the Catalog service.
 * It sets up the server, controllers, and exception handling.
 */
@Slf4j
public class Main {

    private static final String JSON_TYPE = "application/json";
    private static final Integer PORT = Integer.parseInt(Ports.CATALOG_SERVICE_PORT.getPort());

    /**
     * The main method for the Catalog service.
     * It starts the server, sets up the controllers, and sets up exception handling.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        log.info(String.format("Catalog server started on port %d", PORT));
        port(PORT);
        setupControllers();
        setupExceptionHandling();
        after((req, res) ->
                res.type(JSON_TYPE)
        );
    }

    /**
     * This method sets up the controllers for the Catalog service.
     * It creates instances of the CategoryService and BookService, and then creates instances of the CategoryController and BookController with these services.
     */
    private static void setupControllers() {
        CategoryService categoryService = new CategoryService();
        BookService bookService = new BookService();

        new CategoryController(categoryService);
        new BookController(bookService);
    }

    /**
     * This method sets up exception handling for the Catalog service.
     * It logs any unhandled exceptions and sends a 500 response with the exception message.
     */
    private static void setupExceptionHandling() {
        exception(Exception.class, (exception, request, response) -> {
            log.error("Unhandled exception", exception);
            response.status(500);
            response.body("Server error occurred: " + exception.getMessage());
        });
    }
}