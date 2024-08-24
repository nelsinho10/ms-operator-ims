package com.unir.ms_operator.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unir.ms_operator.model.pojo.OrderProduct;
import com.unir.ms_operator.model.pojo.OrderProductId;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

    @Query("SELECT op FROM OrderProduct op WHERE op.id.orderId = :orderId")
    List<OrderProduct> findByOrderId(@Param("orderId") Long orderId);
}