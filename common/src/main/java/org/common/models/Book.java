package org.common.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.common.csv.CsvColumn;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

/**
 * This class represents a Book entity in the application.
 * It uses Lombok annotations for automatic generation of getters, setters, equals, hashCode and toString methods.
 * It also uses validation annotations to ensure that the fields meet the requirements.
 */
@Data
@RequiredArgsConstructor
public class Book {
    /**
     * The ID of the book. It cannot be blank.
     */
    @NotBlank(message = "ID cannot be blank")
    @CsvColumn(name = "ID")
    private String id;

    /**
     * The title of the book. It cannot be blank.
     */
    @NotBlank(message = "Title cannot be blank")
    @CsvColumn(name = "Title")
    private String title;

    /**
     * The price of the book. It must be positive or zero.
     */
    @PositiveOrZero(message = "Price must be positive or zero")
    @CsvColumn(name = "Price")
    private Double price;

    /**
     * The quantity of the book in stock. It must be positive or zero.
     */
    @PositiveOrZero(message = "Quantity must be positive or zero")
    @CsvColumn(name = "Quantity")
    private Integer quantity;

    /**
     * The category of the book. It cannot be blank.
     */
    @NotBlank(message = "Category cannot be blank")
    @CsvColumn(name = "Category")
    private String categoryName;
}