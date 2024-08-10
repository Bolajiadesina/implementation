package com.security.implementation.security.basicAuth.services;


import lombok.Data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.implementation.security.basicAuth.controller.EmployeeController;
import com.security.implementation.security.basicAuth.model.Employee;
import com.security.implementation.security.basicAuth.repository.EmployeeRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeDetailsService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);  
    private  EmployeeRepository employeeRepository ;

    public EmployeeDetailsService (EmployeeRepository employeeRepository){
        this.employeeRepository= employeeRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);

        Set<GrantedAuthority> authorities = employee.getRoles().stream()
                .map((roles) -> new SimpleGrantedAuthority(roles.getName()))
                .collect(Collectors.toSet());

               // Logger.info(authorities);

        return new org.springframework.security.core.userdetails.User(
                username,
                employee.getPassword(),
                authorities
        );

    }
}
