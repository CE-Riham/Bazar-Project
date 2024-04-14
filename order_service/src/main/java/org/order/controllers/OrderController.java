package org.order.controllers;

import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.common.enums.StatusCode;
import org.common.enums.urls.BookUrl;
import org.common.models.Book;
import org.common.models.Order;
import org.common.utils.HttpRequestSender;
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
        path("/api/order/place-order", () -> {
            post("", this::createOrder, gson::toJson);

        });
        path("/api/admin/order", () -> {
            get(ORDER_ID_PATH, this::getOrderById, gson::toJson);
            delete(ORDER_ID_PATH, this::deleteOrder, gson::toJson);
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


    private Object getOrderById(spark.Request req, spark.Response res) {
        String orderId = req.params(ODER_ID_PARAMETER);
        log.info("get order by id method");
        return checkOrderExists(orderId, res);
    }

    private Object createOrder(spark.Request req, spark.Response res) {
        log.info("create new order method");
        Order newOrder = gson.fromJson(req.body(), Order.class);
        String BookServiceUrl = "http://catalog_service:8081/api/book/";
        String UpadteBookServiceUrl = "http://catalog_service:8081/api/admin/book/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BookServiceUrl + newOrder.getBookId());

            String response = client.execute(request, httpResponse ->
                    EntityUtils.toString(httpResponse.getEntity()));

            Book book = gson.fromJson(response, Book.class);
            if(book.getQuantity() < newOrder.getQuantity() || book.getQuantity() <= 0 || newOrder.getQuantity() <= 0){
                return new MessageResponse("Not enough books in stock");
            }

            if(book.getPrice()*newOrder.getQuantity() > newOrder.getPaidAmount()){
                return new MessageResponse("Paid amount is not enough to buy the books");
            }

            HttpPut secondRequest = new HttpPut(UpadteBookServiceUrl + newOrder.getBookId());

            String json = String.format("{\"quantity\": %d}", book.getQuantity() - newOrder.getQuantity());

            secondRequest.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            client.execute(secondRequest, httpResponse ->
                    EntityUtils.toString(httpResponse.getEntity()));

            newOrder.setPaidAmount(book.getPrice()*newOrder.getQuantity());

            newOrder = orderService.createOrder(newOrder);
        } catch (Exception e) {
            return e.toString();
        }
        res.status(StatusCode.CREATED.getCode());
        return newOrder;
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
