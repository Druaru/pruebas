package com.cursos.api.springsecuritycourse.exception;

@SuppressWarnings("serial")
public class InvalidPasswordException extends RuntimeException{

	public InvalidPasswordException() {
		super();
	}

	public InvalidPasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPasswordException(String message) {
		super(message);
	}

	
}
