package org.order.services;

import org.common.csv.CsvReader;
import org.common.csv.CsvWriter;
import org.common.models.Order;
import org.common.parsers.OrderParser;
import org.common.repository.Repository;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class OrderService {
    private static final String ORDERS_FILE_PATH = "order_service/data/Orders.csv";
    private final Repository<Order> orderRepository;

    public OrderService() {
        File ordersFile = Paths.get("data", "Orders.csv").toFile();
        orderRepository = new Repository<>(ORDERS_FILE_PATH, new OrderParser());
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAll();
    }

    public Order getOrderById(Integer orderId) {
//        return orderCsvReader.getObjectWithCondition("orderId", orderId.toString());
        return null;
    }

    public void createOrder(Order newOrder) {

//        orderCsvWriter.insertObject(newOrder);
    }
}