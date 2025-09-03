package com.example.electricitymanagement.controller;

import com.example.electricitymanagement.model.Bill;
import com.example.electricitymanagement.model.Customer;
import com.example.electricitymanagement.model.Payment;
import com.example.electricitymanagement.service.BillService;
import com.example.electricitymanagement.service.CustomerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private BillService billService;

    @GetMapping("/")
    public String showHomePage() {
        return "index"; // Landing page
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String registerCustomer(@Valid @ModelAttribute("customer") Customer customer,
                                   BindingResult result,
                                   Model model) {

        // 1. Password match check
        if (!customer.getPassword().equals(customer.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match");
        }

        // 2. Uniqueness checks
        if (customerService.isLoginIdTaken(customer.getLoginId())) {
            result.rejectValue("loginId", "error.loginId", "Login ID already exists");
        }

        if (customerService.isEmailTaken(customer.getEmail())) {
            result.rejectValue("email", "error.email", "Email already registered");
        }

        if (customerService.isPhoneTaken(customer.getPhone())) {
            result.rejectValue("phone", "error.phone", "Phone number already registered");
        }

        // 3. If any errors, show back form
        if (result.hasErrors()) {
            return "register";
        }

        // 4. Save
        customerService.registerCustomer(customer);
        model.addAttribute("registeredCustomer", customer);
        return "register-success";

    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    public String processLogin(@ModelAttribute("customer") Customer customer,
                               Model model, HttpSession session) {
        String loginId = customer.getLoginId();
        String password = customer.getPassword();

        // Admin Login
        if ("admin".equalsIgnoreCase(loginId) && "admin123".equals(password)) {
            return "admin";  // Loads admin.html
        }

        // Customer Login
        Customer existingCustomer = customerService.getCustomerByLoginIdAndPassword(loginId, password);
        if (existingCustomer != null) {
            session.setAttribute("customerId", existingCustomer.getCustomerId()); // ✅ Store in session
            session.setAttribute("customerName", existingCustomer.getName()); // Do this after successful login/register
            model.addAttribute("customer", existingCustomer);
            return "redirect:/dashboard";  // Loads dashboard.html
        } else {
            model.addAttribute("error", "Invalid login credentials");
            return "login";
        }
    }

    

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");
        String customerName = (String) session.getAttribute("customerName");
        if (customerId == null) return "redirect:/login";

        double totalConsumption = billService.getTotalConsumption(customerId);
        double lastPayment = billService.getLastPayment(customerId);
        double paymentDue = billService.getPaymentDue(customerId);

        model.addAttribute("totalConsumption", totalConsumption);
        model.addAttribute("lastPayment", lastPayment);
        model.addAttribute("paymentDue", paymentDue);
        model.addAttribute("customerName", customerName);
        return "dashboard";
    }

    @GetMapping("/mybills")
    public String viewMyBills(HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");

        if (customerId == null) {
            return "redirect:/login";
        }

        List<Bill> bills = billService.getBillsByCustomerId(customerId);
        model.addAttribute("bills", bills);
        return "mybills";
    }

    
    //paybill
 // Show payment form with pending bills
    @GetMapping("/paybill")
    public String showPayBillForm(HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }

        List<Bill> pendingBills = billService.getPendingBillsByCustomerId(customerId);
        model.addAttribute("pendingBills", pendingBills);
        model.addAttribute("payment", new Payment()); // For form binding

        return "paybill";
    }

    // Process the payment
    @PostMapping("/paybill")
    public String processBillPayment(@Valid @ModelAttribute("payment") Payment payment,
                                     BindingResult result,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            // On validation failure, reload pending bills and return to form
            List<Bill> pendingBills = billService.getPendingBillsByCustomerId(customerId);
            model.addAttribute("pendingBills", pendingBills);
            return "paybill";
        }

        // Set transaction date manually
        payment.setTransactionDate(LocalDate.now());

        String resultMessage = billService.payBill(payment,customerId); // Assuming payBill now accepts Payment object
        redirectAttributes.addFlashAttribute("message", resultMessage);

        return "redirect:/mybills";
    }



} 

