package org.catalog.services;

import org.common.csv.CsvReader;
import org.common.csv.CsvWriter;
import org.common.models.Category;
import org.common.parsers.CategoryParser;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class CategoryService {
    private static final String CATEGORIES_FILE_PATH = "catalog_service/data/Categories.csv";

    private final CsvReader<Category> categoryCsvReader;
    private final CsvWriter<Category> categoryCsvWriter;

    public CategoryService() {
        File categoriesFile = Paths.get("data", "Categories.csv").toFile();

        categoryCsvReader = new CsvReader<>(CATEGORIES_FILE_PATH, CategoryParser.stringArrayToCategory);
        categoryCsvWriter = new CsvWriter<>(CATEGORIES_FILE_PATH, CategoryParser.categoryToStringArray);
    }

    public List<Category> getAllCategories() {
        return categoryCsvReader.getAll();
    }

    public Category getCategoryById(String id) {
        return categoryCsvReader.getObjectWithCondition("ID", id);
    }

    public void createCategory(Category newCategory) {
        newCategory.setId(UUID.randomUUID().toString());
        categoryCsvWriter.insertObject(newCategory);
    }
}
