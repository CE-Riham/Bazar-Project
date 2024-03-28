package org.order.controllers;

import com.google.gson.Gson;
import org.common.enums.StatusCode;
import org.common.models.Order;
import org.order.services.OrderService;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class OrderController {
    private final OrderService orderService;
    private final Gson gson = new Gson();
    private static final String ORDER_ID_PATH = "/:order-id";
    private static final String ODER_ID_PARAMETER = ":order-id";

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        setupRoutes();
    }

    private void setupRoutes() {
        path("/api/order", () -> {
            get("", this::getAllOrders, gson::toJson);
            get(ORDER_ID_PATH, this::getOrderById, gson::toJson);
        });
        path("/api/admin/order", () -> {
            post("", this::createOrder);
            put(ORDER_ID_PATH, this::updateOrder);
            delete(ORDER_ID_PATH, this::deleteOrder);
        });
    }

    private ApiResponse getAllOrders(spark.Request req, spark.Response res) {
        return new ApiResponse(StatusCode.SUCCESS, orderService.getAllOrders());
    }

    private ApiResponse getOrderById(spark.Request req, spark.Response res) {
        String orderId = req.params(ODER_ID_PARAMETER);
        return new ApiResponse(StatusCode.SUCCESS, orderService.getOrderById(orderId));
    }

    private ApiResponse createOrder(spark.Request req, spark.Response res) {
        Order newOrder = gson.fromJson(req.body(), Order.class);
        orderService.createOrder(newOrder);
        return new ApiResponse(StatusCode.SUCCESS);
    }

    private ApiResponse updateOrder(Request req, Response res) {
        Order newOrder = gson.fromJson(req.body(), Order.class);
        newOrder.setId(req.params(ODER_ID_PARAMETER));
        orderService.updateOrderById(req.params(ODER_ID_PARAMETER), newOrder);
        return new ApiResponse(StatusCode.SUCCESS);
    }

    private ApiResponse deleteOrder(Request req, Response res) {
        String orderId = req.params(ODER_ID_PARAMETER);
        orderService.deleteOrderById(orderId);
        return new ApiResponse(StatusCode.SUCCESS);
    }

}
