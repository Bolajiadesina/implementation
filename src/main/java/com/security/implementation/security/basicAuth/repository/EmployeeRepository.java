package com.security.implementation.security.basicAuth.repository;



import java.util.List;

import org.springframework.stereotype.Service;

import com.security.implementation.security.basicAuth.model.Employee;
import com.security.implementation.security.basicAuth.model.EmployeeRequest;
import com.security.implementation.security.basicAuth.model.Response;
@Service
public interface EmployeeRepository  {
    Employee findByUsername(String username);
    Response getAllEmployee();
    Response saveUser(EmployeeRequest employee);
    Response updateUser(EmployeeRequest employee);
}
