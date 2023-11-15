package com.cursos.api.springsecuritycourse.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cursos.api.springsecuritycourse.persistence.repository.security.RoleRepository;
import com.cursos.api.springsecuritycourse.persistence.security.Role;
import com.cursos.api.springsecuritycourse.service.RoleService;

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
