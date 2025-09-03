package com.example.electricitymanagement.service;

import com.example.electricitymanagement.model.Bill;
import com.example.electricitymanagement.model.Payment;
import com.example.electricitymanagement.repository.BillRepository;
import com.example.electricitymanagement.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void saveBill(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public List<Bill> getBillsByCustomerId(Long customerId) {
        return billRepository.findByCustomerCustomerId(customerId);
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }
    
    @Override
    public List<Bill> getPendingBillsByCustomerId(Long customerId) {
        return billRepository.findByCustomerCustomerIdAndStatusIn(
            customerId,
            List.of("Unpaid", "Partial Paid")
        );
    }


    @Override
    public String payBill(Payment payment, Long customerId) {
        Optional<Bill> optionalBill = billRepository.findById(payment.getBillId());

        if (optionalBill.isEmpty()) {
            return "Failed: Bill not found.";
        }

        Bill bill = optionalBill.get();

        // Authorization check
        if (!bill.getCustomer().getCustomerId().equals(customerId)) {
            return "Failed: Unauthorized access to bill.";
        }

        double dueAmount = bill.getBillAmount() - bill.getPaidAmount();

        if (dueAmount <= 0) {
            return "Failed: Bill already fully paid.";
        }

        if (payment.getAmountPaid() == null || payment.getAmountPaid() <= 0) {
            return "Failed: Amount can't be negative or zero.";
        }

        if (payment.getAmountPaid() > dueAmount) {
            return "Failed: Amount can't be greater than ₹" + dueAmount;
        }

        // Set transaction fields
        payment.setTransactionDate(LocalDate.now());
        //payment.setTransactionStatus("Success");

        // Save the payment (card details saved here)
        paymentRepository.save(payment);

        // Update bill
        bill.setPaidAmount(bill.getPaidAmount() + payment.getAmountPaid());

        if (Double.compare(bill.getPaidAmount(), bill.getBillAmount()) == 0) {
            bill.setStatus("Paid");
        } else {
            bill.setStatus("Partially Paid");
        }

        billRepository.save(bill);

        return "Payment of ₹" + payment.getAmountPaid() + " successful for Bill ID: " + payment.getBillId();
    }



    
    
    @Override
    public Double getTotalConsumption(Long customerId) {
        List<Bill> bills = billRepository.findByCustomerCustomerId(customerId);
        return bills.stream()
                .mapToDouble(Bill::getMeterReading)
                .sum();
    }

    @Override
    public Double getLastPayment(Long customerId) {
        return 750.0; // Hardcoded last payment amount
    }


    @Override
    public Double getPaymentDue(Long customerId) {
        List<Bill> pendingBills = billRepository.findByCustomerCustomerIdAndStatusIn(
            customerId, List.of("Unpaid", "Partial Paid"));
        return pendingBills.stream()
                .mapToDouble(Bill::getBillAmount)
                .sum();
    }
    
    @Override
    public List<Bill> searchByBillIdOrCustomerId(String query) {
        try {
            int billId = Integer.parseInt(query);  // For billId (int)
            Optional<Bill> bill = billRepository.findById(billId);
            return bill.map(List::of).orElse(List.of());
        } catch (NumberFormatException e) {
            try {
                Long customerId = Long.parseLong(query);  // For customerId (Long)
                return billRepository.findByCustomer_CustomerId(customerId);
            } catch (NumberFormatException ex) {
                return List.of(); // Not a number at all
            }
        }
    }


}
