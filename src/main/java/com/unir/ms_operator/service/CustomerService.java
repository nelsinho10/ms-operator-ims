package com.unir.ms_operator.service;

import java.util.List;

import com.unir.ms_operator.model.pojo.Customer;
import com.unir.ms_operator.model.request.CreateCustomerRequest;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomer(Long id);
    Customer createCustomer(CreateCustomerRequest request);
    Customer updateCustomer(Long id, Customer customer);
    Boolean deleteCustomer(Long id);
    List<Customer> searchCustomer(String name);
}
