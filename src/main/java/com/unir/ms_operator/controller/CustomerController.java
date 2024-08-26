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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomer(id);

        if (customer == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/customers/search")
    public ResponseEntity<List<Customer>> searchCustomer(@RequestParam String name) {
        List<Customer> customers = customerService.searchCustomer(name);

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

    @PostMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestHeader Map<String, String> headers,
            @RequestBody Customer customer) {
        log.info("headers: {}", headers);
        Customer updatedCustomer = customerService.updateCustomer(Long.parseLong(headers.get("id")), customer);

        if (updatedCustomer == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(updatedCustomer);
    }

    @PostMapping("/customers/{id}/delete")
    public ResponseEntity<Boolean> deleteCustomer(@RequestHeader Map<String, String> headers) {
        log.info("headers: {}", headers);
        Boolean deleted = customerService.deleteCustomer(Long.parseLong(headers.get("id")));

        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(deleted);
    }

}
