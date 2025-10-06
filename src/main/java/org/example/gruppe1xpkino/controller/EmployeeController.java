package org.example.gruppe1xpkino.controller;

import org.example.gruppe1xpkino.model.Employee;
import org.example.gruppe1xpkino.model.EmployeeRole;
import org.example.gruppe1xpkino.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // ---------------- Signup ----------------
    @PostMapping("/signup")
    public Employee signup(@RequestBody SignupRequest request) {
        Employee employee = new Employee();

        employee.setName(request.getFirstName() + " " + request.getLastName());
        employee.setUsername(request.getUsername()); // NEW
        employee.setPassword(request.getPassword());
        employee.setRole(EmployeeRole.valueOf(request.getRole()));

        return employeeRepository.save(employee);
    }

    // ---------------- Login ----------------
    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequest request) {
        Employee employee = employeeRepository.findByUsername(request.getUsername()).orElse(null);

        return employee != null && employee.getPassword().equals(request.getPassword());
    }

    // ---------------- DTOs ----------------
    static class SignupRequest {
        private String firstName;
        private String lastName;
        private String username; // NEW
        private String password;
        private String role;

        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getUsername() { return username; } // NEW
        public void setUsername(String username) { this.username = username; } // NEW

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

    static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}