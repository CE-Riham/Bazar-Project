package org.catalog.controllers;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.catalog.services.CategoryService;
import org.common.enums.StatusCode;
import org.common.enums.urls.BookUrl;
import org.common.enums.urls.CategoryUrl;
import org.common.models.Category;
import org.common.url_builders.BookUrlBuilder;
import org.common.utils.HttpRequestSender;
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
            get(CategoryUrl.GET_CATEGORY_BY_ID.getUrl(), this::getCategoryById, gson::toJson);
            get(CategoryUrl.GET_ALL_BOOKS_BY_CATEGORY_ID.getUrl(), this::getAllBooksInCategory, gson::toJson);
        });
        path(CategoryUrl.CATEGORY_ADMIN_API_PATH.getUrl(), () -> {
            post(CategoryUrl.CREATE_CATEGORY_PATH.getUrl(), this::createCategory, gson::toJson);
            put(CategoryUrl.UPDATE_CATEGORY_BY_ID_PATH.getUrl(), this::updateCategory, gson::toJson);
        });
    }

    private Object checkCategoryExists(String categoryId, spark.Response res) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            res.status(StatusCode.OK.getCode());
            return category;
        } else {
            res.status(StatusCode.NOT_FOUND.getCode());
            return String.format("Category with id = %s is not found", categoryId);
        }
    }

    private Object getCategoryById(spark.Request req, spark.Response res) {
        log.info("get category by id method");
        String categoryId = req.params(CATEGORY_ID_PARAMETER);
        Object checkResult = checkCategoryExists(categoryId, res);
        if (res.status() == StatusCode.OK.getCode()) {
            return checkResult;
        }
        res.status(StatusCode.NOT_FOUND.getCode());
        return new MessageResponse((String) checkResult);
    }

    private Object getAllBooksInCategory(spark.Request req, spark.Response res) {
        log.info("get all books in category method");
        String categoryId = req.params(CATEGORY_ID_PARAMETER);
        Object errorMessage = checkCategoryExists(categoryId, res);
        if (res.status() == StatusCode.OK.getCode()) {
            return categoryService.getBooksByCategory(categoryId);
        }
        res.status(StatusCode.NOT_FOUND.getCode());
        return new MessageResponse((String) errorMessage);
    }

    private Object createCategory(spark.Request req, spark.Response res) {
        log.info("create new category method");
        Category category = gson.fromJson(req.body(), Category.class);
        Category addedCategory = categoryService.createCategory(category);
        res.status(StatusCode.CREATED.getCode());
        return addedCategory;
    }

    private MessageResponse fetchUpdateBooksCategory(String categoryId, String json, spark.Response res) {
        String updateBooksCategoryUrl = BookUrlBuilder.updateBooksCategoryUrl(categoryId);
        // send PUT request to catalog server
        String updateCategoryResponse = HttpRequestSender.sendPutRequest(updateBooksCategoryUrl, json, res);
        return gson.fromJson(updateCategoryResponse, MessageResponse.class);
    }

    private Object updateCategory(spark.Request req, spark.Response res) {
        log.info("update category method");
        Category category = gson.fromJson(req.body(), Category.class);
        String categoryId = req.params(CATEGORY_ID_PARAMETER);
        category.setId(categoryId);
        String json = gson.toJson(category);
        MessageResponse fetchUpdateResponse = fetchUpdateBooksCategory(categoryId, json, res);
        if (res.status() == StatusCode.OK.getCode()) {
            Category updatedCategory = categoryService.updateCategory(categoryId, category);
            res.status(StatusCode.OK.getCode());
            return updatedCategory;
        }
        return fetchUpdateResponse;
    }
}
