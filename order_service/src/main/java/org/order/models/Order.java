package org.order.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@RequiredArgsConstructor
public class Order {
    @NotBlank(message = "orderId cannot be blank")
    private Integer orderId;

    @NotBlank(message = " bookId cannot be blank")
    private Integer bookId;

    @NotBlank(message = " quantity cannot be blank")
    @PositiveOrZero(message = "quantity must be positive or zero")
    private Integer quantity;

    @NotBlank(message = "  paidAmount cannot be blank")
    @PositiveOrZero(message = " paidAmount must be positive or zero")
    private Double paidAmount;


}
