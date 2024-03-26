package org.order.controllers;

import com.google.gson.Gson;
import org.common.models.Order;
import org.order.services.OrderService;

import static spark.Spark.*;

public class OrderController {
    private final OrderService orderService;
    private final Gson gson = new Gson();

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        setupRoutes();
    }

    private void setupRoutes() {
        path("/order", () -> {
            get("", this::getAllOrders, gson::toJson);
            get("/:orderId", this::getOrderById, gson::toJson);
            post("", this::createOrder);
        });
    }

    private Object getAllOrders(spark.Request req, spark.Response res) {
        return orderService.getAllOrders();
    }

    private Object getOrderById(spark.Request req, spark.Response res) {
        int orderId = Integer.parseInt(req.params(":orderId"));
        return orderService.getOrderById(orderId);
    }

    private String createOrder(spark.Request req, spark.Response res) {
        Order newOrder = gson.fromJson(req.body(), Order.class);
        orderService.createOrder(newOrder);
        return "Order created successfully";
    }

}
