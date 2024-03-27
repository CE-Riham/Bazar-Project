package org.common.enums.urls;

import org.common.enums.Ports;

public enum CatalogServiceUrl {
    CATALOG_SERVICE_BASE("http://catalog_service:" + Ports.CATALOG_SERVICE_PORT),
    CATEGORY_API_PATH("/api/category"),
    CATEGORY_ADMIN_API_PATH("/api/admin/category"),
    CATEGORY_ID_PARAMETER(":category-id"),
    GET_CATEGORY_BY_ID_PATH("/" + CATEGORY_ID_PARAMETER.getUrl()),
    CREATE_CATEGORY_PATH(""),
    UPDATE_CATEGORY_BY_ID_PATH("/" + CATEGORY_ID_PARAMETER.getUrl()),
    DELETE_CATEGORY_BY_ID_PATH("/" + CATEGORY_ID_PARAMETER.getUrl());
    private final String url;

    CatalogServiceUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}