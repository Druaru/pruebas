package com.drr.springboot.backend.apirest.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drr.springboot.backend.apirest.persistence.security.User;


public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);

}
