package com.unir.ms_operator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.unir.ms_operator.data.CustomerRepository;
import com.unir.ms_operator.model.pojo.Customer;
import com.unir.ms_operator.model.request.CreateCustomerRequest;

@Service
public class CustomersServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.isEmpty() ? null : customers;
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer createCustomer(CreateCustomerRequest request) {
        if (request != null && StringUtils.hasLength(request.getName().trim()) &&
                StringUtils.hasLength(request.getAddress().trim()) &&
                StringUtils.hasLength(request.getPhone().trim()) &&
                StringUtils.hasLength(request.getEmail().trim())) {

            Customer customer = Customer.builder().name(request.getName()).address(request.getAddress())
                    .phone(request.getPhone()).email(request.getEmail()).status(true).build();

            return customerRepository.save(customer);

        } else {
            return null;
        }
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        if (customerRepository.existsById(id)) {
            customer.setId(id);
            return customerRepository.save(customer);
        } else {
            return null;
        }
    }

    @Override
    public Boolean deleteCustomer(Long id) {
       if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Customer> searchCustomer(String name) {
       if (StringUtils.hasLength(name.trim())) {
            return customerRepository.findByNameContaining(name);
        } else {
            return null;
        }
    }

}
