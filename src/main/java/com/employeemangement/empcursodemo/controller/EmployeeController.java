package com.employeemangement.empcursodemo.controller;

import com.employeemangement.empcursodemo.entity.Employee;
import com.employeemangement.empcursodemo.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
@Tag(name = "Employee Management", description = "APIs for managing employee data")
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    // Display all employees (Thymeleaf view)
    @GetMapping
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("employeeCount", employeeService.getEmployeeCount());
        return "employees/list";
    }
    
    // Show employee form for creating new employee
    @GetMapping("/new")
    public String showEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/form";
    }
    
    // Create new employee
    @PostMapping
    public String createEmployee(@ModelAttribute Employee employee, Model model) {
        if (employeeService.employeeExistsByEmail(employee.getEmail())) {
            model.addAttribute("error", "Employee with this email already exists!");
            model.addAttribute("employee", employee);
            return "employees/form";
        }
        
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }
    
    // Show employee details
    @GetMapping("/{id}")
    public String showEmployeeDetails(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employees/details";
        } else {
            model.addAttribute("error", "Employee not found!");
            return "redirect:/employees";
        }
    }
    
    // Show edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employees/form";
        } else {
            model.addAttribute("error", "Employee not found!");
            return "redirect:/employees";
        }
    }
    
    // Update employee
    @PostMapping("/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee, Model model) {
        Optional<Employee> existingEmployee = employeeService.getEmployeeByEmail(employee.getEmail());
        if (existingEmployee.isPresent() && !existingEmployee.get().getId().equals(id)) {
            model.addAttribute("error", "Email already exists for another employee!");
            model.addAttribute("employee", employee);
            return "employees/form";
        }
        
        employeeService.updateEmployee(id, employee);
        return "redirect:/employees";
    }
    
    // Delete employee
    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
    
    // Search employees
    @GetMapping("/search")
    public String searchEmployees(@RequestParam String searchTerm, Model model) {
        List<Employee> employees = employeeService.searchEmployeesByName(searchTerm);
        model.addAttribute("employees", employees);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("employeeCount", employees.size());
        return "employees/list";
    }
    
    // Filter by department
    @GetMapping("/department/{department}")
    public String getEmployeesByDepartment(@PathVariable String department, Model model) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        model.addAttribute("employees", employees);
        model.addAttribute("department", department);
        model.addAttribute("employeeCount", employees.size());
        return "employees/list";
    }
    
    // REST API endpoints for JSON responses
    
    @GetMapping("/api")
    @ResponseBody
    @Operation(
        summary = "Get all employees",
        description = "Retrieves a list of all employees in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved all employees",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        )
    })
    public ResponseEntity<List<Employee>> getAllEmployeesApi() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/api/{id}")
    @ResponseBody
    @Operation(
        summary = "Get employee by ID",
        description = "Retrieves a specific employee by their unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved employee",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found"
        )
    })
    public ResponseEntity<Employee> getEmployeeByIdApi(
        @Parameter(description = "Employee ID", example = "1")
        @PathVariable Long id
    ) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/api")
    @ResponseBody
    @Operation(
        summary = "Create new employee",
        description = "Creates a new employee in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Employee created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Employee with this email already exists"
        )
    })
    public ResponseEntity<Employee> createEmployeeApi(
        @Parameter(description = "Employee object to create", required = true)
        @RequestBody Employee employee
    ) {
        if (employeeService.employeeExistsByEmail(employee.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }
    
    @PutMapping("/api/{id}")
    @ResponseBody
    @Operation(
        summary = "Update employee",
        description = "Updates an existing employee's information"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Employee updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Email already exists for another employee"
        )
    })
    public ResponseEntity<Employee> updateEmployeeApi(
        @Parameter(description = "Employee ID", example = "1")
        @PathVariable Long id,
        @Parameter(description = "Updated employee object", required = true)
        @RequestBody Employee employee
    ) {
        Optional<Employee> existingEmployee = employeeService.getEmployeeByEmail(employee.getEmail());
        if (existingEmployee.isPresent() && !existingEmployee.get().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        Optional<Employee> updatedEmployee = employeeService.updateEmployee(id, employee);
        return updatedEmployee.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/api/{id}")
    @ResponseBody
    @Operation(
        summary = "Delete employee",
        description = "Deletes an employee from the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Employee deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found"
        )
    })
    public ResponseEntity<String> deleteEmployeeApi(
        @Parameter(description = "Employee ID", example = "1")
        @PathVariable Long id
    ) {
        boolean deleted = employeeService.deleteEmployee(id);
        if (deleted) {
            return ResponseEntity.ok("Employee deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/api/search")
    @ResponseBody
    @Operation(
        summary = "Search employees by name",
        description = "Searches for employees by first name or last name (case-insensitive)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Search results retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        )
    })
    public ResponseEntity<List<Employee>> searchEmployeesApi(
        @Parameter(description = "Search term for employee name", example = "john")
        @RequestParam String searchTerm
    ) {
        List<Employee> employees = employeeService.searchEmployeesByName(searchTerm);
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/api/department/{department}")
    @ResponseBody
    @Operation(
        summary = "Get employees by department",
        description = "Retrieves all employees in a specific department"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Department employees retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        )
    })
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentApi(
        @Parameter(description = "Department name", example = "IT")
        @PathVariable String department
    ) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/api/salary/range")
    @ResponseBody
    @Operation(
        summary = "Get employees by salary range",
        description = "Retrieves employees within a specified salary range"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Salary range results retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Employee.class)
            )
        )
    })
    public ResponseEntity<List<Employee>> getEmployeesBySalaryRangeApi(
        @Parameter(description = "Minimum salary", example = "50000")
        @RequestParam Double minSalary,
        @Parameter(description = "Maximum salary", example = "80000")
        @RequestParam Double maxSalary
    ) {
        List<Employee> employees = employeeService.getEmployeesBySalaryRange(minSalary, maxSalary);
        return ResponseEntity.ok(employees);
    }
    
    @GetMapping("/api/count")
    @ResponseBody
    @Operation(
        summary = "Get employee count",
        description = "Retrieves the total number of employees in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Employee count retrieved successfully"
        )
    })
    public ResponseEntity<Long> getEmployeeCountApi() {
        long count = employeeService.getEmployeeCount();
        return ResponseEntity.ok(count);
    }
}
