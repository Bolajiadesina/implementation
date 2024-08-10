package com.security.implementation.security.basicAuth.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.security.implementation.security.basicAuth.model.Employee;
import com.security.implementation.security.basicAuth.model.EmployeeRequest;
import com.security.implementation.security.basicAuth.model.Response;
import com.security.implementation.security.basicAuth.repository.EmployeeRepository;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);  

    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository){
            this.employeeRepository=employeeRepository;
    }

    @GetMapping("/getAllEmployees")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Response getAllEmployees(){
       
        return employeeRepository.getAllEmployee();
    }

    @PostMapping("/saveEmployees")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response saveEmployees(@RequestBody EmployeeRequest employee){
        return employeeRepository.saveUser(employee);
    }

    @PutMapping("/updateEmployees")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateEmployees(@RequestBody EmployeeRequest employee){
        return employeeRepository.updateUser(employee);
    }

}
