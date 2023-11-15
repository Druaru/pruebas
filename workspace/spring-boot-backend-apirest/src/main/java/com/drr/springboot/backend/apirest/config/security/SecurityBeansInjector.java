package com.drr.springboot.backend.apirest.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.drr.springboot.backend.apirest.exception.ObjectNotFoundException;
import com.drr.springboot.backend.apirest.persistence.repository.security.UserRepository;


@Configuration
public class SecurityBeansInjector {
	
	@Autowired
	private UserRepository userRepository;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();

	}

	@Bean
	public AuthenticationProvider autenthicationProvider() {

		DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
		authenticationStrategy.setPasswordEncoder(passwordEncoder());
		authenticationStrategy.setUserDetailsService(userDetailsService());

		return authenticationStrategy;

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return (username) -> {
			return userRepository.findByUsername(username)
					.orElseThrow(() -> new ObjectNotFoundException("User not found with username " + username));
		};
	}

}
