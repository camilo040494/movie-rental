package com.github.camilo.movierental.exception;

public class UserNotFoundException extends ApplicationEntityNotFoundException {

    /**
     * 
     */
    private static final long serialVersionUID = 7788174838771716572L;
    
    public UserNotFoundException(String message) {
        super(message);
    }

}
