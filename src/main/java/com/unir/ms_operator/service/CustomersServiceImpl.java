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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustomer'");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCustomer'");
    }

    @Override
    public Boolean deleteCustomer(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCustomer'");
    }

}
