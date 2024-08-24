package com.unir.ms_operator.service;

import java.util.List;

import com.unir.ms_operator.model.pojo.Order;
import com.unir.ms_operator.model.pojo.Product;
import com.unir.ms_operator.model.request.CreateOrderRequest;

public interface OrderService {
    List<Order> getOrders();
    Order getOrder(Long id);
    Order createOrder(CreateOrderRequest request);
    Order updateOrder(Long id, Order order);
    Boolean deleteOrder(Long id);
    Boolean changeStatus(Long id, Boolean status);
    List<Product> getProductsByOrderId(Long id);
}
