package org.catalog.models;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class Category {
    @NotBlank(message = "ID cannot be blank")
    private int id;
    private List<Book> booksList;

    public Category(int id, List<Book> booksList) {
        this.id = id;
        this.booksList = booksList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }
}
