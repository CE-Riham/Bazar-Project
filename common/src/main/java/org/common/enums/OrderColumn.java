package org.common.enums;

public enum OrderColumn {
    ID("Id"),
    QUANTITY("Quantity"),
    PAIDAMOUNT("PaidAmount"),
    BOOKID("bookId");
    private final String columnName;

    OrderColumn(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return this.columnName;
    }
}
