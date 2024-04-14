package org.order.services;

import org.common.enums.columns.OrderColumn;
import org.common.models.Order;
import org.common.parsers.OrderParser;
import org.common.repository.Repository;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private static final String ORDERS_FILE_PATH = "order_service/data/Orders.csv";
    private final Repository<Order> orderRepository;

    public OrderService() {
        File ordersFile = null;

        try {
            ordersFile = Paths.get("data", "Orders.csv").toFile();

            if (!ordersFile.exists() || ordersFile.isDirectory()) {
                throw new Exception("Default file path is not valid.");
            }
        } catch (Exception e) {
            ordersFile = new File(ORDERS_FILE_PATH);
        }

        orderRepository = new Repository<>(ordersFile, new OrderParser());
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAll();
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getObjectBy(OrderColumn.ID.toString(), orderId);
    }

    public Order createOrder(Order newOrder) {
        newOrder.setId(UUID.randomUUID().toString());
        orderRepository.add(newOrder);
        return newOrder;
    }

    public Order updateOrderById(String id, Order newOrder) {
        orderRepository.updateObjectsBy(OrderColumn.ID.toString(), id, newOrder);
        return getOrderById(id);
    }

    public void deleteOrderById(String id) {
        orderRepository.deleteObjectsBy(OrderColumn.ID.toString(), id);
    }
}