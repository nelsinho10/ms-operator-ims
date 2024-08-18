package com.unir.ms_operator.controller;

import org.springframework.web.bind.annotation.RestController;

import com.unir.ms_operator.model.pojo.Customer;
import com.unir.ms_operator.model.request.CreateCustomerRequest;
import com.unir.ms_operator.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers(@RequestHeader Map<String, String> headers) {
        log.info("headers: {}", headers);
        List<Customer> customers = customerService.getCustomers();

        if (customers == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customers);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody CreateCustomerRequest request) {

        try {
            Customer customer = customerService.createCustomer(request);

            if (customer == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(201).body(customer);

        } catch (Exception e) {
            log.error("Error creating customer");
            // Return message error to client
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();

        }

    }

}
