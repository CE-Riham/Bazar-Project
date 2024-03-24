package org.catalog.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class BookDTO {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @PositiveOrZero(message = "Quantity must be positive or zero")
    private Integer quantity;

    @PositiveOrZero(message = "Price must be positive or zero")
    private Integer price;

    // Constructors (including a no-arg constructor for frameworks that require it)
    public BookDTO() {
    }

    public BookDTO(String title, Integer quantity, Integer price) {
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
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
}
