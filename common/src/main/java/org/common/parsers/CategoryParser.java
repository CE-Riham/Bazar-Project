package org.common.parsers;

import org.common.models.Category;

/**
 * This class implements the ParserInterface for the Category entity.
 * It provides methods to convert a Category object to a string array and vice versa.
 */
public class CategoryParser implements ParserInterface<Category> {

    /**
     * This method converts a string array to a Category object.
     * It expects the string array to have exactly 2 elements (ID, name).
     * If the string array does not have exactly 2 elements, it throws an IllegalArgumentException.
     * @param line The string array to be converted to a Category object.
     * @return The Category object.
     */
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

    /**
     * This method converts a Category object to a string array.
     * The string array will have exactly 2 elements (ID, name).
     * @param object The Category object to be converted to a string array.
     * @return The string array.
     */
    @Override
    public String[] toStringArray(Category object) {
        String[] row = new String[2];
        row[0] = object.getId();
        row[1] = object.getName();
        return row;
    }
}