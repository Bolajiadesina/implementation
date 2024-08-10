package com.security.implementation.security.basicAuth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    
    private String  employeeId;
    private String name;
    private String userName;
  
    private String email;
    
    private String password;

    
}
