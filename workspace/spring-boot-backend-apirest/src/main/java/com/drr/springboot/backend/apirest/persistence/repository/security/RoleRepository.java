package com.drr.springboot.backend.apirest.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drr.springboot.backend.apirest.persistence.security.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(String defaultRole);

}
