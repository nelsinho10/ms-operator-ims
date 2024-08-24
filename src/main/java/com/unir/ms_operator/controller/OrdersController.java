package com.unir.ms_operator.controller;

import org.springframework.web.bind.annotation.RestController;

import com.unir.ms_operator.model.pojo.Order;
import com.unir.ms_operator.model.pojo.Product;
import com.unir.ms_operator.model.request.CreateOrderRequest;
import com.unir.ms_operator.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrdersController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(@RequestHeader Map<String, String> headers) {
        List<Order> orders = orderService.getOrders();

        if (orders == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(orders);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {

        try {
            Order order = orderService.createOrder(request);

            if (order == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.status(201).body(order);

        } catch (Exception e) {
            log.error("Error creating order", e);
            // Return message error to client
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();

        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@RequestHeader Map<String, String> headers) {
        try {
            Long id = Long.parseLong(headers.get("id"));
            Order order = orderService.getOrder(id);

            if (order == null) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(order);

        } catch (Exception e) {
            log.error("Error getting order");
            // Return message error to client
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/orders/{id}/products")
    public ResponseEntity<List<Product>> getProducts(@PathVariable Long id) {
        try {
            List<Product> products = orderService.getProductsByOrderId(id);
    
            if (products == null) {
                return ResponseEntity.noContent().build();
            }
    
            return ResponseEntity.ok(products);
    
        } catch (Exception e) {
            log.error("Error getting products", e);
            // Return message error to client
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

}
