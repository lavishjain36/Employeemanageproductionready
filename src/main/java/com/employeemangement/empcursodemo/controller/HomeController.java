package com.employeemangement.empcursodemo.controller;

import com.employeemangement.empcursodemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    
    private final EmployeeService employeeService;
    
    @Autowired
    public HomeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("employeeCount", employeeService.getEmployeeCount());
        return "home";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("employeeCount", employeeService.getEmployeeCount());
        return "dashboard";
    }
    
    @GetMapping("/api-docs")
    public String apiDocs() {
        return "redirect:/swagger-ui.html";
    }
}
