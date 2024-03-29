package org.catalog.controllers;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.catalog.services.CategoryService;
import org.common.enums.StatusCode;
import org.common.enums.urls.CategoryUrl;
import org.common.models.Category;
import org.common.utils.MessageResponse;

import static spark.Spark.*;

@Log
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

    private Object checkCategoryExists(String categoryId, spark.Response res) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            res.status(StatusCode.OK.getCode());
            return category;
        } else {
            res.status(StatusCode.NOT_FOUND.getCode());
            return String.format("Category with id: %s is not found", categoryId);
        }
    }

    private Object getAllBooksInCategory(spark.Request req, spark.Response res) {
        log.info("get all books in category method");
        String categoryId = req.params(CATEGORY_ID_PARAMETER);
        checkCategoryExists(categoryId, res);
        if (res.status() == 200) {
            return categoryService.getBooksByCategory(categoryId);
        }
        res.status(StatusCode.NOT_FOUND.getCode());
        return new MessageResponse("Category not found");
    }

    private Object createCategory(spark.Request req, spark.Response res) {
        log.info("create new category method");
        Category category = gson.fromJson(req.body(), Category.class);
        Category category1 = categoryService.createCategory(category);
        res.status(StatusCode.CREATED.getCode());
        return category1;
    }
}
