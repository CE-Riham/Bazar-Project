package org.common.url_builders;

import org.common.enums.urls.BookUrl;
import org.common.enums.urls.CategoryUrl;

public class BookUrlBuilder {
    private static String bookAdminPath = CategoryUrl.CATALOG_SERVICE_BASE.getUrl() + BookUrl.BOOK_ADMIN_API_PATH.getUrl();

    public static String updateBooksCategoryUrl(String categoryId) {
        return bookAdminPath + BookUrl.BOOKS_CATEGORY_PATH.getUrl() + "/" + categoryId;
    }
}
