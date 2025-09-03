package com.example.electricitymanagement.service;

import com.example.electricitymanagement.model.Customer;
import com.example.electricitymanagement.repository.CustomerRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private BillService billService;

    @Override
    public void registerCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean isLoginIdTaken(String loginId) {
        return customerRepository.existsByLoginId(loginId);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean isPhoneTaken(String phone) {
        return customerRepository.existsByPhone(phone);
    }
    
    @Override
    public Customer getCustomerByLoginIdAndPassword(String loginId, String password) {
        return customerRepository.findByLoginIdAndPassword(loginId, password);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }
    @Override
    public String deleteCustomerIfNoDues(Long customerId) {
        double dueAmount = billService.getPaymentDue(customerId);
        if (dueAmount == 0.0) {
            customerRepository.deleteById(customerId);
            return "Customer deleted successfully.";
        } else {
            return "Cannot delete customer. Pending dues: ₹" + dueAmount;
        }
    }

}
