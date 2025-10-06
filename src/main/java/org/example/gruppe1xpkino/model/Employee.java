package org.example.gruppe1xpkino.model;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

    private String name;

    @Column(unique = true)
    private String username; // NEW

    private String password;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public EmployeeRole getRole() { return role; }
    public void setRole(EmployeeRole role) { this.role = role; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; } // NEW
    public void setUsername(String username) { this.username = username; } // NEW

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}