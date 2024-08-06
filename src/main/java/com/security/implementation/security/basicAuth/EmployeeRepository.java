package com.security.implementation.security.basicAuth;



import java.util.List;

import org.springframework.stereotype.Service;
@Service
public interface EmployeeRepository  {
    Employee findByUsername(String username);
    List<Employee> getAllEmployee();
}
