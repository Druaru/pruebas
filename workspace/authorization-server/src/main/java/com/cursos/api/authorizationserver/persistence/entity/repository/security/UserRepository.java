package com.cursos.api.authorizationserver.persistence.entity.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursos.api.authorizationserver.persistence.entity.security.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);

}
