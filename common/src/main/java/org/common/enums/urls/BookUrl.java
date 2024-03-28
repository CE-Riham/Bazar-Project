package org.common.enums.urls;


public enum BookUrl {
    BOOK_API_PATH("/api/book"),
    BOOK_ADMIN_API_PATH("/api/admin/book"),
    BOOK_ID_PARAMETER(":book-id"),
    GET_ALL_BOOKS_PATH(""),
    GET_BOOK_BY_ID_PATH("/" + BOOK_ID_PARAMETER.getUrl()),
    CREATE_BOOK_PATH(""),
    UPDATE_BOOK_BY_ID_PATH("/" + BOOK_ID_PARAMETER.getUrl()),
    DELETE_BOOK_BY_ID_PATH("/" + BOOK_ID_PARAMETER.getUrl());
    private final String url;

    BookUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}