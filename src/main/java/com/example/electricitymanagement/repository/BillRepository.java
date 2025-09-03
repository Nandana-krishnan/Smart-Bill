package com.example.electricitymanagement.repository;

import com.example.electricitymanagement.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Integer> {
	// Fetch bills for a specific customer
    List<Bill> findByCustomerCustomerId(Long customerId);
    List<Bill> findByCustomer_CustomerId(Long customerId);

    // Fetch pending bills for a customer
    List<Bill> findByCustomerCustomerIdAndStatusIn(Long customerId, List<String> statuses);

	Optional<Bill> findById(int billId);
    
}
