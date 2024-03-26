package org.catalog.services;

import org.common.models.Category;
import org.common.parsers.CategoryParser;
import org.common.repository.Repository;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public class CategoryService {
    private static final String CATEGORIES_FILE_PATH = "catalog_service/data/Categories.csv";
    private final Repository<Category> categoryRepository;

    public CategoryService() {
        File categoriesFile = Paths.get("data", "Categories.csv").toFile();
        categoryRepository = new Repository<>(CATEGORIES_FILE_PATH, new CategoryParser());
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAll();
    }

    public Category getCategoryById(String id) {
        return null;
//        return categoryCsvReader.getObjectWithCondition("ID", id);
    }

    public void createCategory(Category newCategory) {
        newCategory.setId(UUID.randomUUID().toString());
//        categoryCsvWriter.insertObject(newCategory);
    }
}
