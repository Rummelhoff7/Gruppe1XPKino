package org.example.gruppe1xpkino.repository;

import org.example.gruppe1xpkino.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUsername(String username); // updated to username
}