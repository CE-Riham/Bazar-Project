package org.frontTier.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.common.enums.StatusCode;
import org.common.enums.urls.BookUrl;
import org.common.enums.urls.CategoryUrl;
import org.common.enums.urls.FrontTierUrl;
import org.common.models.Book;
import org.common.models.Order;
import org.common.utils.MessageResponse;
import org.frontTier.services.FrontTierService;
import spark.Request;
import spark.Response;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static spark.Spark.*;
import static spark.Spark.put;

public class FrontTierController {

    private final FrontTierService frontTierService;
    private final Gson gson = new Gson();

    public FrontTierController(FrontTierService bookService) {
        this.frontTierService = bookService;
        setupRoutes();
    }

    private void setupRoutes() {
        path(FrontTierUrl.FRONT_TIER_BASE.getUrl(), () -> {
            get(FrontTierUrl.BOOK_API_PATH_BY_TITLE.getUrl() +"/"+ FrontTierUrl.BOOK_NAME_PARAMETER.getUrl(), this::getBooksByTitle, gson::toJson);
            get(FrontTierUrl.BOOK_API_PATH.getUrl() +"/"+ FrontTierUrl.BOOK_ID_PARAMETER.getUrl(), this::getBookById, gson::toJson);
            get(FrontTierUrl.CATEGORY_BOOKS_PATH.getUrl()+ "/"+FrontTierUrl.CATEGORY_NAME_PARAMETER.getUrl(), this::getCategoryBooks, gson::toJson);
            post(FrontTierUrl.PURCHASE_PATH.getUrl(), this::purchaseBook, gson::toJson);
        });

    }

    private Object purchaseBook(spark.Request req, spark.Response res) {
        // Create HttpClient inside try-with-resources for proper resource management
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Create HttpPost request
            HttpPost request = new HttpPost("http://order_service:8082/api/order/place-order");

            // Correctly set the entity with the request body content
            StringEntity entity = new StringEntity(req.body(), ContentType.APPLICATION_JSON);
            request.setEntity(entity);

            // Execute the request and handle the response
            HttpResponse httpResponse = client.execute(request);

            // Convert the response entity to string and then parse it into Order object
            String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
            Order order = new Gson().fromJson(jsonResponse, Order.class);

            return order;

        } catch (Exception e) {
            // Handle exception appropriately; you might want to log this or handle differently
            e.printStackTrace();
            return null; // Or consider a custom error object/response
        }
    }


    private Object getCategoryBooks(spark.Request req, spark.Response res) {
        res.status(StatusCode.OK.getCode());
        String categoryName = req.params(FrontTierUrl.CATEGORY_NAME_PARAMETER.getUrl());
        try {
            String encodedCategoryName = URLEncoder.encode(categoryName, StandardCharsets.UTF_8.name()).replace("+", "%20");

            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet("http://catalog_service:8081/api/category/"+ encodedCategoryName+"/books");
                HttpResponse httpResponse = client.execute(request);
                Type listType = new TypeToken<List<Book>>() {}.getType();
                List<Book> books = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), listType);
                return books;
            }
        } catch (Exception e) {
            return ("Exception" + "Error while making HTTP request: " + e.toString());
        }
    }

    private Object getBookById(spark.Request req, spark.Response res) {
        res.status(StatusCode.OK.getCode());
        String bookId = req.params(FrontTierUrl.BOOK_ID_PARAMETER.getUrl());
        try {

            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet("http://catalog_service:8081/api/book/" + bookId);
                HttpResponse httpResponse = client.execute(request);
                Book book = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), Book.class);
                return book;
            }
        } catch (Exception e) {
            return ("Exception"+ "Error while making HTTP request: " + e.toString());
        }
    }

    private Object getBooksByTitle(spark.Request req, spark.Response res) {
        res.status(StatusCode.OK.getCode());
        String bookName = req.params(FrontTierUrl.BOOK_NAME_PARAMETER.getUrl());
        try {
            String encodedBookName = URLEncoder.encode(bookName, StandardCharsets.UTF_8.name()).replace("+", "%20");

            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet("http://catalog_service:8081/api/book/title/" + encodedBookName);
                HttpResponse httpResponse = client.execute(request);

                Type listType = new TypeToken<List<Book>>() {}.getType();
                List<Book> books = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), listType);
                return books;
            }
        } catch (Exception e) {


            return ("Exception" + "Error while making HTTP request: " + e.toString());
        }
    }
}
