package org.common.parsers;


import org.common.models.Order;

public class OrderParser implements ParserInterface<Order> {
    @Override
    public Order toObject(String[] line) {
        if (line.length != 4) {
            throw new IllegalArgumentException("Invalid line format");
        }
        try {
            Order order = new Order();
            order.setId(line[0].trim());
            order.setBookId(line[1].trim());
            order.setQuantity(Integer.parseInt(line[2].trim()));
            order.setPaidAmount(Double.parseDouble(line[3].trim()));
            return order;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing number", e);
        }
    }

    @Override
    public String[] toStringArray(Order object) {
        String[] row = new String[4];
        row[0] = object.getId();
        row[1] = object.getBookId();
        row[2] = object.getQuantity().toString();
        row[3] = object.getPaidAmount().toString();
        return row;
    }
}
