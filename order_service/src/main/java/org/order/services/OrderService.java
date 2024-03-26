package org.order.services;

import org.common.csv.CsvReader;
import org.common.csv.CsvWriter;
import org.common.models.Order;
import org.common.parsers.OrderParser;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class OrderService {
    private static final String ORDERS_FILE_PATH = "order_service/data/Orders.csv";

    private final CsvReader<Order> orderCsvReader;
    private final CsvWriter<Order> orderCsvWriter;

    public OrderService() {
        File ordersFile = Paths.get("data", "Orders.csv").toFile();

        orderCsvReader = new CsvReader<>(ORDERS_FILE_PATH, OrderParser.stringArrayToOrder);
        orderCsvWriter = new CsvWriter<>(ORDERS_FILE_PATH, OrderParser.orderToStringArray);
    }

    public List<Order> getAllOrders() {
        return orderCsvReader.getAll();
    }

    public Order getOrderById(Integer orderId) {
        return orderCsvReader.getObjectWithCondition("orderId", orderId.toString());
    }

    public void createOrder(Order newOrder) {
        orderCsvWriter.insertObject(newOrder);
    }
}