package org.catalog.controllers;

import com.google.gson.Gson;
import org.catalog.services.CategoryService;
import org.common.enums.StatusResponse;
import org.common.enums.urls.CategoryUrl;
import org.common.models.Book;
import org.common.models.Category;
import org.common.utils.ApiResponse;

import java.util.List;

import static spark.Spark.*;


public class CategoryController {

    private final CategoryService categoryService;
    private final Gson gson = new Gson();
    private static final String CATEGORY_ID_PARAMETER = CategoryUrl.CATEGORY_ID_PARAMETER.getUrl();

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
        setupRoutes();
    }

    private void setupRoutes() {
        path(CategoryUrl.CATEGORY_API_PATH.getUrl(), () -> {
            get(CategoryUrl.GET_ALL_BOOKS_BY_CATEGORY_ID.getUrl(), this::getAllBooksInCategory, gson::toJson);
        });
        path(CategoryUrl.CATEGORY_ADMIN_API_PATH.getUrl(), () -> {
            post(CategoryUrl.CREATE_CATEGORY_PATH.getUrl(), this::createCategory, gson::toJson);
        });
    }

    private ApiResponse getAllBooksInCategory(spark.Request req, spark.Response res) {
        List<Book> books = categoryService.getBooksByCategory(req.params(CATEGORY_ID_PARAMETER));
        return new ApiResponse(StatusResponse.SUCCESS, books);
    }

    private ApiResponse createCategory(spark.Request req, spark.Response res) {
        Category category = gson.fromJson(req.body(), Category.class);
        categoryService.createCategory(category);
        return new ApiResponse(StatusResponse.SUCCESS);
    }
}
