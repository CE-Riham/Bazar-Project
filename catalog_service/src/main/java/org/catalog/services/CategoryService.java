package org.catalog.services;

import org.common.enums.columns.BookColumn;
import org.common.enums.columns.CategoryColumn;
import org.common.models.Book;
import org.common.models.Category;
import org.common.parsers.BookParser;
import org.common.parsers.CategoryParser;
import org.common.repository.Repository;

import java.util.List;
import java.util.UUID;

public class CategoryService {
    private static final String CATEGORIES_FILE_PATH = "catalog_service/data/Categories.csv";
    private static final String BOOKS_FILE_PATH = "catalog_service/data/Books.csv";

    private final Repository<Category> categoryRepository;
    private final Repository<Book> bookRepository;

    public CategoryService() {
        categoryRepository = new Repository<>(CATEGORIES_FILE_PATH, new CategoryParser());
        bookRepository = new Repository<>(BOOKS_FILE_PATH, new BookParser());
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAll();
    }

    public Category getCategoryById(String id) {
        return categoryRepository.getObjectBy(CategoryColumn.ID.toString(), id);
    }

    public List<Book> getBooksByCategory(String categoryID) {
        // get category name for categoryID
        Category category = categoryRepository.getObjectBy(CategoryColumn.ID.toString(), categoryID);
        return bookRepository.getObjectsBy(BookColumn.CATEGORY.toString(), category.getName());
    }

    public Category createCategory(Category newCategory) {
        newCategory.setId(UUID.randomUUID().toString());
        categoryRepository.add(newCategory);
        return newCategory;
    }

    public Category updateCategory(String id, Category newCategory) {
        categoryRepository.updateObjectsBy(CategoryColumn.ID.toString(), id, newCategory);
        return getCategoryById(id);
    }

    public void deleteCategoryById(String id) {
        categoryRepository.deleteObjectsBy(CategoryColumn.ID.toString(), id);
    }
}
