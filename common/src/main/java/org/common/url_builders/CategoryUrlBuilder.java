package org.common.url_builders;

import org.common.enums.urls.CategoryUrl;

public class CategoryUrlBuilder {
    private static String categoryPath = CategoryUrl.CATALOG_SERVICE_BASE.getUrl() + CategoryUrl.CATEGORY_API_PATH.getUrl();

    public static String getCategoryByIdUrl(String categoryId) {
        return categoryPath + "/" + categoryId;
    }
}
