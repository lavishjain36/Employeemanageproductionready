package com.employeemangement.empcursodemo.service;

import com.employeemangement.empcursodemo.entity.Employee;
import com.employeemangement.empcursodemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    // Get employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    // Get employee by email
    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    
    // Get employees by department
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }
    
    // Search employees by name
    public List<Employee> searchEmployeesByName(String searchTerm) {
        return employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                searchTerm, searchTerm);
    }
    
    // Get employees with salary greater than specified amount
    public List<Employee> getEmployeesWithSalaryGreaterThan(Double minSalary) {
        return employeeRepository.findEmployeesWithSalaryGreaterThan(minSalary);
    }
    
    // Save employee
    public Employee saveEmployee(Employee employee) {
        // Set hire date if not provided
        if (employee.getHireDate() == null) {
            employee.setHireDate(LocalDate.now());
        }
        return employeeRepository.save(employee);
    }
    
    // Update employee
    public Optional<Employee> updateEmployee(Long id, Employee employeeDetails) {
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setFirstName(employeeDetails.getFirstName());
                    existingEmployee.setLastName(employeeDetails.getLastName());
                    existingEmployee.setEmail(employeeDetails.getEmail());
                    existingEmployee.setPhone(employeeDetails.getPhone());
                    existingEmployee.setSalary(employeeDetails.getSalary());
                    existingEmployee.setDepartment(employeeDetails.getDepartment());
                    return employeeRepository.save(existingEmployee);
                });
    }
    
    // Delete employee
    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Check if employee exists by email
    public boolean employeeExistsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }
    
    // Get employee count
    public long getEmployeeCount() {
        return employeeRepository.count();
    }
    
    // Get employees by department and salary range
    public List<Employee> getEmployeesByDepartmentAndSalaryRange(String department, Double minSalary, Double maxSalary) {
        return employeeRepository.findEmployeesByDepartmentAndSalaryRange(department, minSalary, maxSalary);
    }
    
    // Get employees by salary range
    public List<Employee> getEmployeesBySalaryRange(Double minSalary, Double maxSalary) {
        return employeeRepository.findEmployeesBySalaryRange(minSalary, maxSalary);
    }
}
