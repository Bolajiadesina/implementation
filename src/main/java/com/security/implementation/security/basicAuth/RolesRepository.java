package com.security.implementation.security.basicAuth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository   extends JpaRepository<Roles,Long> {
    
}
