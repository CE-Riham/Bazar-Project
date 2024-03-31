package org.common.enums.urls;

import org.common.enums.Ports;

public enum OrderServiceUrl {
//    ORDER_SERVICE_BASE("http://order_service:" + Ports.ORDER_SERVICE_PORT.getPort());
    ORDER_SERVICE_BASE("localhost:" + Ports.ORDER_SERVICE_PORT.getPort());
//    CATEGORY_API_PATH("/api/category"),
//    CATEGORY_ADMIN_API_PATH("/api/admin/category"),
//    CATEGORY_ID_PARAMETER(":category-id"),
//    GET_CATEGORY_BY_ID_PATH("/" + CATEGORY_ID_PARAMETER.getUrl()),
//    CREATE_CATEGORY_PATH(""),
//    UPDATE_CATEGORY_BY_ID_PATH("/" + CATEGORY_ID_PARAMETER.getUrl()),
//    DELETE_CATEGORY_BY_ID_PATH("/" + CATEGORY_ID_PARAMETER.getUrl());
    private final String url;

    OrderServiceUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}