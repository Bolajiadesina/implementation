package com.security.implementation.security.basicAuth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "employee")
public class Employee {

   
    private String id;
    private String  employeeId;
    private String name;
   
    private String userName;
  
    private String email;
    
    private String password;
    
    private Set<Roles> roles;


}
