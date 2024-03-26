package org.common.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.common.csv.CsvColumn;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@RequiredArgsConstructor
public class Order {

    @NotBlank(message = "orderId cannot be blank")
    @CsvColumn(name = "ID")
    private String id;

    @NotBlank(message = " bookId cannot be blank")
    @CsvColumn(name = "BookId")
    private String bookId;

    @NotBlank(message = " quantity cannot be blank")
    @PositiveOrZero(message = "quantity must be positive or zero")
    @CsvColumn(name = "Quantity")
    private Integer quantity;

    @NotBlank(message = "  paidAmount cannot be blank")
    @PositiveOrZero(message = " paidAmount must be positive or zero")
    @CsvColumn(name = "PaidAmount")
    private Double paidAmount;


}
