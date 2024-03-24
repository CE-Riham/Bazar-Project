package org.catalog.controllers;

import org.catalog.services.BookService;
import org.catalog.services.CategoryService;

import static spark.Spark.*;
import static spark.Spark.put;

public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
        setupRoutes();
    }

    private void setupRoutes() {
        path("/category", () -> {

            // Get a book by ID
            get("/:category-name", (req, res) -> "Get a category by name");

            // Create a new category
            post("", (req, res) -> "Create a new category");

        });
    }
}
