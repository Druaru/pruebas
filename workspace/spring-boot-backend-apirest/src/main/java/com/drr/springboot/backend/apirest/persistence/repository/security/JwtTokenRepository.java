package com.drr.springboot.backend.apirest.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drr.springboot.backend.apirest.persistence.security.JwtToken;


public interface JwtTokenRepository  extends JpaRepository<JwtToken, Long>{

	Optional<JwtToken> findByToken(String jwt);

}
