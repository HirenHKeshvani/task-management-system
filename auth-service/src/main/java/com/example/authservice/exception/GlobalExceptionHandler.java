package com.example.authservice.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntime(RuntimeException ex, HttpServletRequest request) {

		return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {

		return build(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

		String message = ex.getBindingResult().getFieldError().getDefaultMessage();

		return build(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
	}

	private ResponseEntity<?> build(HttpStatus status, String message, String path) {

		return ResponseEntity.status(status).body(
				Map.of("timestamp", LocalDateTime.now(), "status", status.value(), "message", message, "path", path));
	}
}