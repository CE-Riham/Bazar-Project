package org.order.controllers;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.common.enums.StatusCode;
import org.common.models.Order;
import org.common.utils.MessageResponse;
import org.order.services.OrderService;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

@Log
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

    private Object checkOrderExists(String orderId, spark.Response res) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            res.status(StatusCode.OK.getCode());
            return order;
        } else {
            res.status(StatusCode.NOT_FOUND.getCode());
            return String.format("Order with id: %s is not found", orderId);
        }
    }

    private Object getAllOrders(spark.Request req, spark.Response res) {
        log.info("get all orders method");
        res.status(StatusCode.OK.getCode());
        return orderService.getAllOrders();
    }

    private Object getOrderById(spark.Request req, spark.Response res) {
        String orderId = req.params(ODER_ID_PARAMETER);
        log.info("get order by id method");
        return checkOrderExists(orderId, res);
    }

    private Object createOrder(spark.Request req, spark.Response res) {
        log.info("create new order method");
        Order newOrder = gson.fromJson(req.body(), Order.class);
        Order order = orderService.createOrder(newOrder);
        res.status(StatusCode.CREATED.getCode());
        return order;
    }

    private Object updateOrder(Request req, Response res) {
        log.info("update order method");
        String orderId = req.params(ODER_ID_PARAMETER);
        Object checkResult = checkOrderExists(orderId, res);
        if (res.status() == 200) {
            Order newOrder = gson.fromJson(req.body(), Order.class);
            newOrder.setId(req.params(orderId));
            return orderService.updateOrderById(orderId, newOrder);
        }
        return new MessageResponse((String) checkResult);
    }

    private Object deleteOrder(Request req, Response res) {
        log.info("delete order method");
        String orderId = req.params(ODER_ID_PARAMETER);
        Object checkResult = checkOrderExists(orderId, res);
        if (res.status() == 200) {
            orderService.deleteOrderById(orderId);
            return new MessageResponse("Order was deleted successfully.");
        }
        return new MessageResponse((String) checkResult);
    }

}
