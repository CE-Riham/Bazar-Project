package org.catalog.controllers;

import org.catalog.services.CategoryService;
import com.google.gson.Gson;
import org.common.models.Category;

import static spark.Spark.*;


public class CategoryController {

    private final CategoryService categoryService;
    private final Gson gson = new Gson();
    private static final String CATEGORY_NAME_PATH = "/:category-name";
    private static final String CATEGORY_NAME_PARAMETER = ":category-name";

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
        setupRoutes();
    }

    private void setupRoutes() {
        path("/api/category", () -> {
            get(CATEGORY_NAME_PATH, this::getAllBooksInCategory, gson::toJson);
        });
        path("/api/admin/category", () -> {
            post("", this::createCategory, gson::toJson);
        });
    }

    private Object getAllBooksInCategory(spark.Request req, spark.Response res) {
        return categoryService.getBooksByCategory(req.params(CATEGORY_NAME_PARAMETER));
    }

    private String createCategory(spark.Request req, spark.Response res) {
        Category category = gson.fromJson(req.body(), Category.class);
        categoryService.createCategory(category);
        return "Category was Created successfully";
    }
}
