package com.drr.springboot.backend.apirest.models.services;

import java.util.Optional;

import com.drr.springboot.backend.apirest.persistence.security.Role;


public interface RoleService {

	Optional<Role> findDefaultRole();

}