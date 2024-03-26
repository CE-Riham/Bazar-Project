package org.common.parsers;

import org.common.models.Category;

public class CategoryParser implements ParserInterface<Category> {

    @Override
    public Category toObject(String[] line) {
        if (line.length != 2) {
            throw new IllegalArgumentException("Invalid line format");
        }
        try {
            Category category = new Category();
            category.setId(line[0].trim());
            category.setName(line[1].trim());
            return category;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing number", e);
        }
    }

    @Override
    public String[] toStringArray(Category object) {
        String[] row = new String[2];
        row[0] = object.getId();
        row[1] = object.getName();
        return row;
    }
}
