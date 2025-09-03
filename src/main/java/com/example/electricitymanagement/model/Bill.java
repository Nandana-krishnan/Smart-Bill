package com.example.electricitymanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "BILL")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billId;
    @Min(value = 50, message = "Meter reading should be minimum 50")
    private double meterReading;
    @Min(value = 100, message = "Bill Amount should be minimum 100")
    private double billAmount;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable= false)
    private Customer customer;

    private String month;
    private int billYear;
    private String status; 
    
    private double paidAmount = 0.0;


    public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public double getMeterReading() {
		return meterReading;
	}
	public void setMeterReading(double meterReading) {
		this.meterReading = meterReading;
	}
	public double getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getBillYear() {
		return billYear;
	}
	public void setBillYear(int billYear) {
		this.billYear = billYear;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	// "Paid", "Unpaid", "Partial"
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

    // Getters and Setters
}
