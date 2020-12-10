package com.github.camilo.movierental.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.camilo.movierental.exception.ApiError;
import com.github.camilo.movierental.exception.CreationException;
import com.github.camilo.movierental.exception.InvalidFileException;
import com.github.camilo.movierental.exception.MovieNotFoundException;
import com.github.camilo.movierental.exception.NoStockException;
import com.github.camilo.movierental.exception.UserNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { UserNotFoundException.class, MovieNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
      return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }
    
    @ExceptionHandler(value = { NoStockException.class, InvalidFileException.class })
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
    
    @ExceptionHandler(value = { CreationException.class })
    protected ResponseEntity<Object> handleNotFound(CreationException ex, WebRequest request) {
        return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
      return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    
}
