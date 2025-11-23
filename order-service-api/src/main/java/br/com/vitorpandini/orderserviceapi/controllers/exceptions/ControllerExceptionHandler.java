package br.com.vitorpandini.orderserviceapi.controllers.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.GenericFeignException;
import models.exceptions.ResourceNotFoundException;
import models.exceptions.StandardError;
import models.exceptions.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Map;

import static java.time.LocalDateTime.now;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(GenericFeignException.class)
    ResponseEntity<Map> handleGenericFeignException(final GenericFeignException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getError());
    }

    @ExceptionHandler(ResourceNotFoundException .class)
    ResponseEntity<StandardError> handlerResourceNotFoundException(final ResourceNotFoundException ex, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                StandardError.builder()
                        .timestamp(now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<StandardError> handlerDataIntegrityViolationException(final DataIntegrityViolationException ex, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                StandardError.builder()
                        .timestamp(now())
                        .status(HttpStatus.CONFLICT.value())
                        .error(HttpStatus.CONFLICT.getReasonPhrase())
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
