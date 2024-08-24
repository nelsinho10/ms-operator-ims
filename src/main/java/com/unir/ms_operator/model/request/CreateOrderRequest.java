package com.unir.ms_operator.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private String type;
    private Long customerId;
    private Boolean status;
    private CreateProductRequest[] products;
}
