package com.github.camilo.movierental.exception;

public class MovieNotFoundException extends ApplicationEntityNotFoundException {

    /**
     * 
     */
    private static final long serialVersionUID = 3873489384186080364L;

    public MovieNotFoundException(String message) {
        super(message);
    }
    
}
