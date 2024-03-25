package org.common.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@RequiredArgsConstructor
public class Book {
    @NotBlank(message = "ID cannot be blank")
    private String id;

    @NotBlank(message = "Category cannot be blank")
    private String categoryName;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @PositiveOrZero(message = "Quantity must be positive or zero")
    private Integer quantity;

    @PositiveOrZero(message = "Price must be positive or zero")
    private Integer price;
}
