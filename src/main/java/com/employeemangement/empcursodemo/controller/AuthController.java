package com.employeemangement.empcursodemo.controller;

import com.employeemangement.empcursodemo.entity.User;
import com.employeemangement.empcursodemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }
    
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully!");
        }
        
        return "auth/login";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        
        try {
            // Check if username already exists
            if (userService.usernameExists(user.getUsername())) {
                model.addAttribute("error", "Username already exists!");
                return "auth/register";
            }
            
            // Check if email already exists
            if (userService.emailExists(user.getEmail())) {
                model.addAttribute("error", "Email already exists!");
                return "auth/register";
            }
            
            // Register the user
            User registeredUser = userService.registerUser(user);
            
            redirectAttributes.addFlashAttribute("success", 
                "Registration successful! Please login with your credentials.");
            
            return "redirect:/login";
            
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "auth/register";
        }
    }
    
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "auth/access-denied";
    }
    
    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        userService.getUserByUsername(username).ifPresent(user -> {
            model.addAttribute("user", user);
        });
        
        return "auth/profile";
    }
    
    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "auth/profile";
        }
        
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            
            userService.getUserByUsername(currentUsername).ifPresent(currentUser -> {
                user.setId(currentUser.getId());
                user.setUsername(currentUser.getUsername()); // Username cannot be changed
                user.setRole(currentUser.getRole()); // Role cannot be changed by user
                
                // Only update password if provided
                if (user.getPassword() == null || user.getPassword().isEmpty()) {
                    user.setPassword(currentUser.getPassword());
                }
                
                userService.updateUser(currentUser.getId(), user);
            });
            
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
            return "redirect:/profile";
            
        } catch (Exception e) {
            model.addAttribute("error", "Profile update failed: " + e.getMessage());
            return "auth/profile";
        }
    }
    
    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        return "auth/change-password";
    }
    
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New passwords do not match!");
            return "auth/change-password";
        }
        
        if (newPassword.length() < 6) {
            model.addAttribute("error", "New password must be at least 6 characters long!");
            return "auth/change-password";
        }
        
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            userService.getUserByUsername(username).ifPresent(user -> {
                if (userService.changePassword(user.getId(), oldPassword, newPassword)) {
                    redirectAttributes.addFlashAttribute("success", "Password changed successfully!");
                } else {
                    model.addAttribute("error", "Current password is incorrect!");
                }
            });
            
            return "redirect:/profile";
            
        } catch (Exception e) {
            model.addAttribute("error", "Password change failed: " + e.getMessage());
            return "auth/change-password";
        }
    }
}
