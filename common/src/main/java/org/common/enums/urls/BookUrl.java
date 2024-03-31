package org.common.enums.urls;


public enum BookUrl {
    BOOK_API_PATH("/api/book"),
    BOOK_ADMIN_API_PATH("/api/admin/book"),
    BOOK_ID_PARAMETER(":book-id"),
    CATEGORY_ID_PARAMETER(":category-id"),
    GET_ALL_BOOKS_PATH(""),
    GET_BOOK_BY_ID_PATH("/" + BOOK_ID_PARAMETER.getUrl()),
    CREATE_BOOK_PATH(""),
    UPDATE_BOOK_BY_ID_PATH("/" + BOOK_ID_PARAMETER.getUrl()),
    DELETE_BOOK_BY_ID_PATH("/" + BOOK_ID_PARAMETER.getUrl()),
    BOOKS_CATEGORY_PATH("/books-category"),
    UPDATE_BOOKS_CATEGORY_PATH(BOOKS_CATEGORY_PATH.getUrl() + "/" + CATEGORY_ID_PARAMETER.getUrl());
    private final String url;

    BookUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}