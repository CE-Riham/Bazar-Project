package org.common.enums.urls;

public enum FrontTierUrl {
    BOOK_NAME_PARAMETER(":book-name"),
    BOOK_ID_PARAMETER(":book-id"),
    CATEGORY_NAME_PARAMETER(":category-name"),
    FRONT_TIER_BASE("/api"),

    BOOK_API_PATH("/catalog/book"),
    BOOK_API_PATH_BY_TITLE("/catalog/book/title"),
    CATEGORY_BOOKS_PATH("/catalog/category"),
    PURCHASE_PATH("/order/purchase"),


    ///////////////////////////

    ORDER_ID_PARAMETER(":order-id"),
    ADMIN_FRONT_TIER_BASE("/api/admin"),

    ADMIN_ADD_CATEGORY_PATH("/catalog/category/"),
    ADMIN_REMOVE_ORDER_PATH("/order"),
    ADMIN_EDIT_BOOK_PATH("/catalog/book"),
    ADMIN_ADD_BOOK_PATH("/catalog/book"),
    ADMIN_DELETE_BOOK_PATH("/catalog/book");


    private final String url;

    FrontTierUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}