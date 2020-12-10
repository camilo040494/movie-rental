package com.github.camilo.movierental.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreationException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 3567023516743391938L;
    
    private String realCause;
    
    public CreationException(String message, String cause) {
        super(message);
        realCause = cause;
    }
    
}
