package org.common.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.common.csv.CsvColumn;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@RequiredArgsConstructor
public class Book {
    @NotBlank(message = "ID cannot be blank")
    @CsvColumn(name = "ID")
    private String id;

    @NotBlank(message = "Title cannot be blank")
    @CsvColumn(name = "Title")
    private String title;

    @PositiveOrZero(message = "Price must be positive or zero")
    @CsvColumn(name = "Price")
    private Double price;

    @PositiveOrZero(message = "Quantity must be positive or zero")
    @CsvColumn(name = "Quantity")
    private Integer quantity;

    @NotBlank(message = "Category cannot be blank")
    @CsvColumn(name = "Category")
    private String categoryName;
}
