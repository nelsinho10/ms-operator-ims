package com.unir.ms_operator.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.ms_operator.model.pojo.Customer;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByName(String name);
}
