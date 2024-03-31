package org.common.parsers;

import org.common.models.Order;

/**
 * This class implements the ParserInterface for the Order entity.
 * It provides methods to convert an Order object to a string array and vice versa.
 */
public class OrderParser implements ParserInterface<Order> {

    /**
     * This method converts a string array to an Order object.
     * It expects the string array to have exactly 4 elements (ID, bookId, quantity, paidAmount).
     * If the string array does not have exactly 4 elements, it throws an IllegalArgumentException.
     * If the quantity or paidAmount cannot be parsed to a number, it throws an IllegalArgumentException.
     * @param line The string array to be converted to an Order object.
     * @return The Order object.
     */
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

    /**
     * This method converts an Order object to a string array.
     * The string array will have exactly 4 elements (ID, bookId, quantity, paidAmount).
     * @param object The Order object to be converted to a string array.
     * @return The string array.
     */
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