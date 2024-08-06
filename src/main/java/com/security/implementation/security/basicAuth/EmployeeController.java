package com.security.implementation.security.basicAuth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public List<Employee> getAllEmployees(){
        logger.info("get here");
        return employeeRepository.getAllEmployee();
    }

    @PostMapping("/saveEmployees")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveEmployees(){
        return "You saved a Employee";
    }

    @PutMapping("/updateEmployees")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateEmployees(){
        return "You updated a Employee";
    }

}
