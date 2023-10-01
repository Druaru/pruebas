package com.drr.springboot.backend.apirest.config.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsService usuarioService;
	
		
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfiguration()))
				.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/**").authenticated())
				.httpBasic(withDefaults());
		http.userDetailsService(usuarioService);
		return http.build();
	}

	/*
	 * @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	 * Exception {
	 * 
	 * http.cors(corsConfigurer ->
	 * corsConfigurer.configurationSource(corsConfiguration()))
	 * .csrf(AbstractHttpConfigurer::disable);
	 * http.authorizeHttpRequests(authorizeRequests ->
	 * authorizeRequests.requestMatchers("/**").anonymous())
	 * .httpBasic(withDefaults()); return http.build(); }
	 */

	@Bean
	CorsConfigurationSource corsConfiguration() {
		return request -> {
			org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowedOriginPatterns(Collections.singletonList("*"));
			config.setAllowCredentials(true);
			return config;
		};
	}
}
