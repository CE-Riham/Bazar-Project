package org.common.parsers;


import org.common.models.Order;

import java.util.function.Function;

public class Parser {
    private Parser() {
        throw new UnsupportedOperationException("Parser class should not be instantiated");
    }

    public static final Function<String[], Order> stringArrayToOrder = cells -> {
        if (cells.length != 4) {
            throw new IllegalArgumentException("Invalid line format");
        }
        try {
            Order order = new Order();
            order.setOrderId(Integer.parseInt(cells[0].trim()));
            order.setBookId(Integer.parseInt(cells[1].trim()));
            order.setQuantity(Integer.parseInt(cells[2].trim()));
            order.setPaidAmount(Double.parseDouble(cells[3].trim()));
            return order;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing number", e);
        }
    };

    public static final Function<Order, String[]> orderToStringArray = order -> {
        String[] row = new String[4];
        row[0] = order.getOrderId().toString();
        row[1] = order.getBookId().toString();
        row[2] = order.getQuantity().toString();
        row[3] = order.getPaidAmount().toString();
        return row;
    };
}
