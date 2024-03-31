package org.common.enums.columns;

public enum CategoryColumn {
    ID("ID"),
    NAME("Name");
    private final String columnName;

    CategoryColumn(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return this.columnName;
    }
}
