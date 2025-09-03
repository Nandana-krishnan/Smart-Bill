
package com.example.electricitymanagement.service;

import com.example.electricitymanagement.model.Bill;
import com.example.electricitymanagement.model.Payment;

import java.util.List;

public interface BillService {
    void saveBill(Bill bill);
    List<Bill> getBillsByCustomerId(Long customerId);
    List<Bill> getAllBills(); // Optional: for admin to view all bills
    //for bill payment
    List<Bill> getPendingBillsByCustomerId(Long customerId);
    //String payBill(Long customerId, int billId, Double amountPaid);
    Double getTotalConsumption(Long customerId);
    Double getLastPayment(Long customerId);
    Double getPaymentDue(Long customerId);
    String payBill(Payment payment,Long customerId);
    List<Bill> searchByBillIdOrCustomerId(String query);
}
