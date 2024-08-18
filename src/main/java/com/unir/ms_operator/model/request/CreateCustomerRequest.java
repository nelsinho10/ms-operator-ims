package com.unir.ms_operator.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {

    private String name;
    private String address;
    private String phone;
    private String email;
}
