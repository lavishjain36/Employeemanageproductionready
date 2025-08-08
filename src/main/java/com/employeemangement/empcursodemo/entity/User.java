package com.employeemangement.empcursodemo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Schema(description = "User entity for authentication and authorization")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the user", example = "1")
    private Long id;
    
    @Column(name = "username", unique = true, nullable = false)
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Unique username for login", example = "johndoe")
    private String username;
    
    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Schema(description = "User's email address (must be unique)", example = "john.doe@company.com")
    private String email;
    
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Schema(description = "Encrypted password", example = "encrypted_password")
    private String password;
    
    @Column(name = "first_name")
    @Schema(description = "User's first name", example = "John")
    private String firstName;
    
    @Column(name = "last_name")
    @Schema(description = "User's last name", example = "Doe")
    private String lastName;
    
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "User's role in the system", example = "USER")
    private Role role = Role.USER;
    
    @Column(name = "enabled", nullable = false)
    @Schema(description = "Whether the user account is enabled", example = "true")
    private boolean enabled = true;
    
    @Column(name = "account_non_expired", nullable = false)
    @Schema(description = "Whether the user account is not expired", example = "true")
    private boolean accountNonExpired = true;
    
    @Column(name = "credentials_non_expired", nullable = false)
    @Schema(description = "Whether the user credentials are not expired", example = "true")
    private boolean credentialsNonExpired = true;
    
    @Column(name = "account_non_locked", nullable = false)
    @Schema(description = "Whether the user account is not locked", example = "true")
    private boolean accountNonLocked = true;
    
    @Column(name = "created_at", nullable = false)
    @Schema(description = "When the user account was created", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
    
    @Column(name = "last_login")
    @Schema(description = "User's last login timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime lastLogin;
    
    // Default constructor
    public User() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor with required fields
    public User(String username, String email, String password, String firstName, String lastName) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
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
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", enabled=" + enabled +
                ", createdAt=" + createdAt +
                '}';
    }
    
    // Role enum
    public enum Role {
        USER, ADMIN
    }
}
