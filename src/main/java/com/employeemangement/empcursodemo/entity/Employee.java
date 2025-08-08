package com.employeemangement.empcursodemo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Schema(description = "Employee entity representing an employee in the system")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the employee", example = "1")
    private Long id;
    
    @Column(name = "first_name", nullable = false)
    @Schema(description = "Employee's first name", example = "John")
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    @Schema(description = "Employee's last name", example = "Doe")
    private String lastName;
    
    @Column(name = "email", unique = true, nullable = false)
    @Schema(description = "Employee's email address (must be unique)", example = "john.doe@company.com")
    private String email;
    
    @Column(name = "phone")
    @Schema(description = "Employee's phone number", example = "+1-555-123-4567")
    private String phone;
    
    @Column(name = "hire_date")
    @Schema(description = "Date when employee was hired", example = "2023-01-15")
    private LocalDate hireDate;
    
    @Column(name = "salary")
    @Schema(description = "Employee's annual salary", example = "75000.00")
    private Double salary;
    
    @Column(name = "department")
    @Schema(description = "Employee's department", example = "IT")
    private String department;
    
    // Default constructor
    public Employee() {}
    
    // Constructor with fields
    public Employee(String firstName, String lastName, String email, String phone, 
                   LocalDate hireDate, Double salary, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.hireDate = hireDate;
        this.salary = salary;
        this.department = department;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public LocalDate getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    
    public Double getSalary() {
        return salary;
    }
    
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", hireDate=" + hireDate +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}
