package com.github.camilo.movierental.exception;

public class NoStockException extends RuntimeException {
    
    public NoStockException(String message) {
        super(message);
    }
    
}
