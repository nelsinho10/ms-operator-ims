package com.unir.ms_operator.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.ms_operator.model.pojo.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
