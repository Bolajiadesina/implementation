package com.security.implementation.security.basicAuth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.implementation.security.basicAuth.model.Roles;

public interface RolesRepository   extends JpaRepository<Roles,Long> {
    
}
