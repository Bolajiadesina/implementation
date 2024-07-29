package com.security.implementation.security.basicAuth;


import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByUsername(String username);
}
