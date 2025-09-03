package com.example.electricitymanagement.service;

import java.util.List;

import com.example.electricitymanagement.model.Customer;

public interface CustomerService {
    void registerCustomer(Customer customer);

    boolean isLoginIdTaken(String loginId);

    boolean isEmailTaken(String email);

    boolean isPhoneTaken(String phone);
    
    Customer getCustomerByLoginIdAndPassword(String loginId, String password);
    
    List<Customer> getAllCustomers();
    
    Customer getCustomerById(Long customerId);

    String deleteCustomerIfNoDues(Long customerId);

}
