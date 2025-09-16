package br.com.vitorpandini.authserviceapi.controllers.exceptions;

import jakarta.servlet.http.HttpServletRequest;

import models.exceptions.ResourceNotFoundException;
import models.exceptions.StandardError;
import models.exceptions.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

import static java.time.LocalDateTime.now;


@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<StandardError> handlerResourceNotFoundException(final BadCredentialsException ex, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                StandardError.builder()
                        .timestamp(now())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationException> handlerMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpServletRequest request) {

        var error = ValidationException.builder()
                .timestamp(now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Exception in validation attibutes")
                .path(request.getRequestURI())
                .errors(new ArrayList<>())
                .build();
        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(error);
    }
}
