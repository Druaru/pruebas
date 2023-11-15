package com.cursos.api.springsecuritycourse.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursos.api.springsecuritycourse.persistence.security.JwtToken;

public interface JwtTokenRepository  extends JpaRepository<JwtToken, Long>{

	Optional<JwtToken> findByToken(String jwt);

}
