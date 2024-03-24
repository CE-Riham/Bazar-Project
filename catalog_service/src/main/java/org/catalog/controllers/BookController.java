package org.catalog.controllers;
import org.catalog.services.BookService;

import static spark.Spark.*;

public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
        setupRoutes();
    }

    private void setupRoutes() {
        path("/book", () -> {

            // Get a book by ID
            get("/:bookId", (req, res) -> "Get a book by ID");

            // Create a new book
            post("", (req, res) -> "Create a new book");

            // Update a book by ID
            put("/:bookId", (req, res) -> "Update a book by ID");


        });

    }
}
