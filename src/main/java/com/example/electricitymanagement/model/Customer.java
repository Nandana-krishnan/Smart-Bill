package com.example.electricitymanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import com.example.electricitymanagement.validation.NoNullSubstring;


@Entity
@Table(name = "customer", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"loginId"}),
    @UniqueConstraint(columnNames = {"email"}),
    @UniqueConstraint(columnNames = {"phone"})
})
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NoNullSubstring(message = "Login ID must not contain the word 'null'")
    @NotBlank(message = "Login ID is required")
    @Size(min = 3, max = 20, message = "Login ID must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Login ID can only contain letters and numbers")
    private String loginId;

    @NoNullSubstring(message = "Name must not contain the word 'null'")
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must only contain letters")
    private String name;

    @NoNullSubstring(message = "Email must not contain the word 'null'")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NoNullSubstring(message = "Address must not contain the word 'null'")
    @NotBlank(message = "Address is required")
    private String address;

    @NoNullSubstring(message = "Phone number must not contain the word 'null'")
    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^(?!1234567890|9876543210|\\d\\1{9})[6-9]\\d{9}$",
        message = "Invalid phone number"
    )
    private String phone;

    @NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
        message = "Password must have 1 uppercase, 1 lowercase, 1 digit, 1 special char, and min 8 chars"
    )
    private String password;

    @Transient
    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    // Getters and Setters

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
