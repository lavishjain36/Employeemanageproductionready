package com.employeemangement.empcursodemo.repository;

import com.employeemangement.empcursodemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Find employee by email
    Optional<Employee> findByEmail(String email);
    
    // Find employees by department
    List<Employee> findByDepartment(String department);
    
    // Find employees by first name or last name containing the given string
    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);
    
    // Custom query to find employees with salary greater than a given amount
    @Query("SELECT e FROM Employee e WHERE e.salary > :minSalary")
    List<Employee> findEmployeesWithSalaryGreaterThan(@Param("minSalary") Double minSalary);
    
    // Custom query to find employees by department and salary range
    @Query("SELECT e FROM Employee e WHERE e.department = :department AND e.salary BETWEEN :minSalary AND :maxSalary")
    List<Employee> findEmployeesByDepartmentAndSalaryRange(
            @Param("department") String department,
            @Param("minSalary") Double minSalary,
            @Param("maxSalary") Double maxSalary);
    
    // Custom query to find employees by salary range
    @Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :minSalary AND :maxSalary")
    List<Employee> findEmployeesBySalaryRange(
            @Param("minSalary") Double minSalary,
            @Param("maxSalary") Double maxSalary);
    
    // Check if employee exists by email
    boolean existsByEmail(String email);
}
