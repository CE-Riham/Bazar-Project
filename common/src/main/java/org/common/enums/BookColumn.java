package org.common.enums;

public enum BookColumn {
    ID("ID"),
    TITLE("Title"),
    PRICE("Price"),
    QUANTITY("Quantity"),
    CATEGORY("Category");

    private final String columnName;

    BookColumn(String columnName) {
        this.columnName = columnName;
    }
    @Override
    public String toString() {
        return this.columnName;
    }
}
