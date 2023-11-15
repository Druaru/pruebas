package com.drr.springboot.backend.apirest.models.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.drr.springboot.backend.apirest.persistence.repository.security.RoleRepository;
import com.drr.springboot.backend.apirest.persistence.security.Role;


@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;

	
	@Value("${security.default.role}")
	private String defaultRole;
	
	@Override
	public Optional<Role> findDefaultRole() {
		return roleRepository.findByName(defaultRole);
	}

}
