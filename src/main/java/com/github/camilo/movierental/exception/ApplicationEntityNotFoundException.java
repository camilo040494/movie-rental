package com.github.camilo.movierental.exception;

public class ApplicationEntityNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 2861847660648211511L;
    
    public ApplicationEntityNotFoundException(String message) {
        super(message);
    }

}
