package com.jsp.food.delivery.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 3, max = 30, message = "* Name must be between 3 and 30 characters")
    private String name;

    @Email(message = "* Enter proper email")
    @NotEmpty(message = "*  Email is required")
    private String email;

    @DecimalMin(value = "6000000000", message = "* Enter proper mobile number")
    @DecimalMax(value = "9999999999", message = "* Enter proper mobile number")
    @NotNull(message = "*  Mobile number is required")
    private Long mobile;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", message = "At least 8 characters and at most 20 characters,one digit,one uppercase letter,one lowercase letter,ne special character and No spaces allowed")
    private String password;

    @Transient
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", message = "At least 8 characters and at most 20 characters,one digit,one uppercase letter,one lowercase letter,ne special character and No spaces allowed")
    private String confirmPassword;

    private Boolean verified;

    private Integer otp;

    private LocalDateTime registrationDate;

    @Size(min = 5, max = 60, message = "* Address must be between 5 and 60 characters")
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders; // One Customer can place many Orders
}
