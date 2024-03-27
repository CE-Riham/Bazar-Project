package org.common.enums.urls;

import org.common.enums.Ports;

public enum BookServiceUrl {
    ORDER_SERVICE_BASE("http://order_service:" + Ports.ORDER_SERVICE_PORT),
    BOOK_API_PATH("/api/book"),
    BOOK_ADMIN_API_PATH("/api/admin/book"),
    BOOK_ID_PARAMETER(":book-id"),
    GET_ALL_BOOKS_PATH(""),
    GET_BOOK_BY_ID_PATH("/" + BOOK_ID_PARAMETER.getUrl()),
    CREATE_BOOK_PATH(""),
    UPDATE_BOOK_BY_ID_PATH("/" + BOOK_ID_PARAMETER.getUrl()),
    DELETE_BOOK_BY_ID_PATH("/" + BOOK_ID_PARAMETER.getUrl());
    private final String url;

    BookServiceUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}