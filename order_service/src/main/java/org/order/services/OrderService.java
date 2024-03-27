package org.order.services;

import org.common.enums.columns.OrderColumn;
import org.common.models.Order;
import org.common.parsers.OrderParser;
import org.common.repository.Repository;

import java.util.List;
import java.util.UUID;

public class OrderService {
    private static final String ORDERS_FILE_PATH = "order_service/data/Orders.csv";
    private final Repository<Order> orderRepository;

    public OrderService() {
//        File ordersFile = Paths.get("data", "Orders.csv").toFile();
        orderRepository = new Repository<>(ORDERS_FILE_PATH, new OrderParser());
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAll();
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getObjectBy(OrderColumn.ID.toString(), orderId);
    }

    public void createOrder(Order newOrder) {
        newOrder.setId(UUID.randomUUID().toString());
        orderRepository.add(newOrder);
    }

    public void updateOrderById(String id, Order newOrder) {
        orderRepository.updateObjectsBy(OrderColumn.ID.toString(), id, newOrder);
    }

    public void deleteOrderById(String id) {
        orderRepository.deleteObjectsBy(OrderColumn.ID.toString(), id);
    }
}