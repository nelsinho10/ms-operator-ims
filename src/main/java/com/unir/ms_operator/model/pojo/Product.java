package com.unir.ms_operator.model.pojo;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;

    private String name;

    private String description;

    private String category;

    private String brand;

    private String model;

    private Double price;

    private Integer stock;

    private String supplier;

    private Boolean enabled;
}
