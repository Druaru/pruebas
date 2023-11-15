package com.cursos.api.springsecuritycourse.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cursos.api.springsecuritycourse.dto.ApiError;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handlerGenericException(HttpServletRequest request, Exception exception) {
		ApiError apiError = new ApiError();
		apiError.setBackendMessage(exception.getLocalizedMessage());
		apiError.setUrl(request.getRequestURL().toString());
		apiError.setMethod(request.getMethod());
		apiError.setTimestamp(LocalDateTime.now());
		apiError.setMessage("Error Interno en el Servidor, vuelva a intentarlo");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handlerMethodArgumentNotValidException(HttpServletRequest request,
			MethodArgumentNotValidException exception) {

		ApiError apiError = new ApiError();
		apiError.setBackendMessage(exception.getLocalizedMessage());
		apiError.setUrl(request.getRequestURL().toString());
		apiError.setMethod(request.getMethod());
		apiError.setTimestamp(LocalDateTime.now());
		apiError.setMessage("Error en la petición enviada");

		System.out.println(
				exception.getAllErrors().stream().map(each -> each.getDefaultMessage()).collect(Collectors.toList()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);

	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handlerAccessDeniedException(HttpServletRequest request, 
			AccessDeniedException exception) {
		ApiError apiError = new ApiError();
		apiError.setBackendMessage(exception.getLocalizedMessage());
		apiError.setUrl(request.getRequestURL().toString());
		apiError.setMethod(request.getMethod());
		apiError.setTimestamp(LocalDateTime.now());
		apiError.setMessage("Acceso Denegado. No tienes los permisos necesarios para acceder a esta función. "
				+ "por favor contacta con el administrador si piensas que esto puede ser un error.");
		apiError.setTimestamp(LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);

	}

}
