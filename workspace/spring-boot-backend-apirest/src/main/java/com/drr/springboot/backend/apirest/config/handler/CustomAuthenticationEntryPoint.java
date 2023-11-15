package com.drr.springboot.backend.apirest.config.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.drr.springboot.backend.apirest.dto.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		ApiError apiError = new ApiError();
		apiError.setBackendMessage(authException.getLocalizedMessage());
		apiError.setUrl(request.getRequestURL().toString());
		apiError.setMethod(request.getMethod());
		apiError.setTimestamp(LocalDateTime.now());
		apiError.setMessage("No se encontraron credenciales de autenticacion."
				+ " Por facor, inicie sesión para acceder a esta función. ");
		apiError.setTimestamp(LocalDateTime.now());
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.registerModule(new JavaTimeModule());
		
		String apiErrorAsJson = objectMapper.writeValueAsString(apiError);
		
		
		response.getWriter().write(apiErrorAsJson);
	}

}
