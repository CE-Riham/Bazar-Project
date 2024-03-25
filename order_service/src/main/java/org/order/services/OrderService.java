package org.order.services;

import org.common.csv.CsvReader;
import org.common.csv.CsvWriter;
import org.common.models.Order;
import org.common.parsers.Parser;
import java.nio.file.Paths;
import java.io.File;
import java.util.List;

public class OrderService {
    private static final String ORDERS_FILE_PATH = "order_service/data/Orders.csv";

    private final CsvReader<Order> orderCsvReader;
    private final CsvWriter<Order> orderCsvWriter;

    public OrderService() {
        File ordersFile = Paths.get("data", "Orders.csv").toFile();

        orderCsvReader = new CsvReader<>(ordersFile, Parser.stringArrayToOrder);
        orderCsvWriter = new CsvWriter<>(ordersFile, Parser.orderToStringArray);
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