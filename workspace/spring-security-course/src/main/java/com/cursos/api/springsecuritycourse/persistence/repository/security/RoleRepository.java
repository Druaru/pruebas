package com.cursos.api.springsecuritycourse.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursos.api.springsecuritycourse.persistence.security.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(String defaultRole);

}
