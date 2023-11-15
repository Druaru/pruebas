package com.cursos.api.springsecuritycourse.config.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.cursos.api.springsecuritycourse.dto.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		ApiError apiError = new ApiError();
		apiError.setBackendMessage(accessDeniedException.getLocalizedMessage());
		apiError.setUrl(request.getRequestURL().toString());
		apiError.setMethod(request.getMethod());
		apiError.setTimestamp(LocalDateTime.now());
		apiError.setMessage("Acceso Denegado. No tienes los permisos necesarios para acceder a esta función. "
				+ "por favor contacta con el administrador si piensas que esto puede ser un error.");
		apiError.setTimestamp(LocalDateTime.now());
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		String apiErrorAsJson = objectMapper.writeValueAsString(apiError);
		
		
		response.getWriter().write(apiErrorAsJson);
	}

}
