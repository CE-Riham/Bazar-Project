package org.order.services;

import org.order.models.Order;
import org.order.utils.CsvReader;
import org.order.utils.CsvWriter;
import org.order.utils.Parser;

import java.util.List;

public class OrderService {
    private static final String ORDERS_FILE_PATH = "order_service/data/Orders.csv";

    private final CsvReader<Order> orderCsvReader;
    private final CsvWriter<Order> orderCsvWriter;

    public OrderService() {
        orderCsvReader = new CsvReader<>(ORDERS_FILE_PATH, Parser.stringArrayToOrder);
        orderCsvWriter = new CsvWriter<>(ORDERS_FILE_PATH, Parser.orderToStringArray);
    }

    public List<Order> getAllOrders() {
        return orderCsvReader.getAll();
    }

    public Order getOrderById(Integer orderId) {
        return orderCsvReader.getRow("orderId", orderId.toString());
    }

    public void createOrder(Order newOrder) {
        orderCsvWriter.insertRow(newOrder);
    }
}