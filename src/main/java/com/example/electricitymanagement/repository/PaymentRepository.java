package com.example.electricitymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.electricitymanagement.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
