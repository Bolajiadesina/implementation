package com.security.implementation.security.basicAuth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    @GetMapping("/getAllEmployees")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String getAllEmployees(){
        return "You Received All Employees List";
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
