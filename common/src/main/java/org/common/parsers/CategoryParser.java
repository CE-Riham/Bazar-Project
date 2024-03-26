package org.common.parsers;

import org.common.models.Category;

import java.util.function.Function;

public class CategoryParser {
    private CategoryParser() {
        throw new UnsupportedOperationException("Category parser class should not be instantiated");
    }

    public static final Function<String[], Category> stringArrayToCategory = cells -> {
        if (cells.length != 2) {
            throw new IllegalArgumentException("Invalid line format");
        }
        try {
            Category category = new Category();
            category.setId(cells[0].trim());
            category.setName(cells[1].trim());
            return category;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing number", e);
        }
    };

    public static final Function<Category, String[]> categoryToStringArray = category -> {
        String[] row = new String[2];
        row[0] = category.getId();
        row[1] = category.getName();
        return row;
    };
}
