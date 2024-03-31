package org.common.enums.columns;

public enum OrderColumn {
    ID("ID"),
    QUANTITY("Quantity"),
    PAIDAMOUNT("PaidAmount"),
    BOOKID("BookId");
    private final String columnName;

    OrderColumn(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return this.columnName;
    }
}
