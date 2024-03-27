package org.catalog.controllers;

import org.catalog.services.CategoryService;
import com.google.gson.Gson;
import org.common.enums.StatusResponse;
import org.common.enums.urls.CatalogServiceUrl;
import org.common.models.ApiResponse;
import org.common.models.Book;
import org.common.models.Category;

import java.util.List;

import static spark.Spark.*;


public class CategoryController {

    private final CategoryService categoryService;
    private final Gson gson = new Gson();
    private static final String CATEGORY_NAME_PARAMETER = CatalogServiceUrl.CATEGORY_ID_PARAMETER.getUrl();

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
        setupRoutes();
    }

    private void setupRoutes() {
        path(CatalogServiceUrl.CATEGORY_API_PATH.getUrl(), () -> {
            get(CatalogServiceUrl.GET_CATEGORY_BY_ID_PATH.getUrl(), this::getAllBooksInCategory, gson::toJson);
        });
        path(CatalogServiceUrl.CATEGORY_ADMIN_API_PATH.getUrl(), () -> {
            post(CatalogServiceUrl.CREATE_CATEGORY_PATH.getUrl(), this::createCategory, gson::toJson);
        });
    }

    private ApiResponse getAllBooksInCategory(spark.Request req, spark.Response res) {
        List<Book> books = categoryService.getBooksByCategory(req.params(CATEGORY_NAME_PARAMETER));
        return new ApiResponse(StatusResponse.SUCCESS, books);
    }

    private ApiResponse createCategory(spark.Request req, spark.Response res) {
        Category category = gson.fromJson(req.body(), Category.class);
        categoryService.createCategory(category);
        return new ApiResponse(StatusResponse.SUCCESS);
    }
}
