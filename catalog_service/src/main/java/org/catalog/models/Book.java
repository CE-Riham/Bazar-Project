package org.catalog.models;

import javax.validation.constraints.*;

public class Book {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @PositiveOrZero(message = "Quantity must be positive or zero")
    private Integer quantity;

    @PositiveOrZero(message = "Price must be positive or zero")
    private Integer price;

    @NotBlank(message = "ID cannot be blank")
    private String id;

    public Book(String title, Integer quantity, Integer price, String id) {
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
