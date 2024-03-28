package org.common.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.common.csv.CsvColumn;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

/**
 * This class represents an Order entity in the application.
 * It uses Lombok annotations for automatic generation of getters, setters, equals, hashCode and toString methods.
 * It also uses validation annotations to ensure that the fields meet the requirements.
 */
@Data
@RequiredArgsConstructor
public class Order {

    /**
     * The ID of the order. It cannot be blank.
     */
    @NotBlank(message = "orderId cannot be blank")
    @CsvColumn(name = "ID")
    private String id;

    /**
     * The ID of the book associated with the order. It cannot be blank.
     */
    @NotBlank(message = " bookId cannot be blank")
    @CsvColumn(name = "BookId")
    private String bookId;

    /**
     * The quantity of the book ordered. It cannot be blank and must be positive or zero.
     */
    @NotBlank(message = " quantity cannot be blank")
    @PositiveOrZero(message = "quantity must be positive or zero")
    @CsvColumn(name = "Quantity")
    private Integer quantity;

    /**
     * The amount paid for the order. It cannot be blank and must be positive or zero.
     */
    @NotBlank(message = "  paidAmount cannot be blank")
    @PositiveOrZero(message = " paidAmount must be positive or zero")
    @CsvColumn(name = "PaidAmount")
    private Double paidAmount;
}