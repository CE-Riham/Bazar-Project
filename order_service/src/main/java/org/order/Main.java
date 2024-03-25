package org.order;

import lombok.extern.slf4j.Slf4j;
import org.order.controllers.OrderController;
import org.order.services.OrderService;

import static spark.Spark.*;

@Slf4j
public class Main {
    private static final Integer PORT = 8082;
    private static final String JSON_TYPE = "application/json";

    public static void main(String[] args) {
        log.info(String.format("Order server started on port %d", PORT));
        port(PORT);
        setupControllers();
        setupExceptionHandling();
        after((req, res) ->
                res.type(JSON_TYPE)
        );
    }

    private static void setupControllers() {
        OrderService categoryService = new OrderService();
        new OrderController(categoryService);
    }

    private static void setupExceptionHandling() {
        exception(Exception.class, (exception, request, response) -> {
            log.error("Unhandled exception" + exception);
            response.status(500); // HTTP 500 Internal Server Error
            response.body("Server error occurred: " + exception.getMessage());
        });
    }
}