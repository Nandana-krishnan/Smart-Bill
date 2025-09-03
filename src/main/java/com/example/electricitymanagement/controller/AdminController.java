

package com.example.electricitymanagement.controller;

import com.example.electricitymanagement.model.Bill;
import com.example.electricitymanagement.model.Customer;
import com.example.electricitymanagement.service.BillService;
import com.example.electricitymanagement.service.CustomerService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BillService billService;
    
    @GetMapping("/admin")
    public String showAdminDashboard() {
        return "admin";  // File: templates/admindashboard.html
    }

    // ✅ Show all registered customers (Admin only)
    @GetMapping("/custlist")
    public String viewCustomerList(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "custlist";
    }

    // ✅ Show the Add Bill form
    @GetMapping("/addbill")
    public String showAddBillForm(Model model,
                                  @RequestParam(value = "success", required = false) String success,
                                  @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("bill", new Bill());
        model.addAttribute("customers", customerService.getAllCustomers());

        if (success != null) {
            model.addAttribute("message", "Bill added successfully!");
        }

        if (error != null && error.equals("invalidCustomer")) {
            model.addAttribute("errorMessage", "Invalid Customer ID. Please enter a valid ID.");
        }

        return "addbill";
    }



    @PostMapping("/addbill")
    public String addBill(@Valid @ModelAttribute("bill") Bill bill,
                          BindingResult result,
                          @RequestParam("customerId") Long customerId,
                          Model model) {

        // If validation errors are present, return to the form with errors
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getAllCustomers());
            return "addbill";
        }

        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return "redirect:/addbill?error=invalidCustomer";
        }

        bill.setCustomer(customer);
        billService.saveBill(bill);
        return "redirect:/addbill?success";
    }

    
    //to view all bills
    @GetMapping("/billlist")
    public String viewAllBills(Model model) {
        List<Bill> bills = billService.getAllBills(); // Make sure this exists
        model.addAttribute("bills", bills);
        return "billlist"; // Loads billlist.html
    }
    
    @GetMapping("/bills/search")
    public String searchBills(@RequestParam("query") String query, Model model) {
        List<Bill> result = billService.searchByBillIdOrCustomerId(query);
        model.addAttribute("bills", result);
        return "billlist"; // same Thymeleaf page
    }

    
    //delete
    @GetMapping("/admin/customers")
    public String showCustomerList(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "custlist"; // ✅ This points to custlist.html
    }

    @PostMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable("id") Long customerId, RedirectAttributes redirectAttributes) {
        if (billService.getPaymentDue(customerId) == 0) {
            customerService.deleteCustomerIfNoDues(customerId);
            redirectAttributes.addFlashAttribute("message", "Customer deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Cannot delete customer with pending dues.");
        }
        return "redirect:/admin/customers";
    }

}
