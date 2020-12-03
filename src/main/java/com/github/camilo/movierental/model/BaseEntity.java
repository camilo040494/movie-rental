package com.github.camilo.movierental.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public abstract class BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5805894828971268428L;
    
    @Column
    @NotNull
    private boolean isDeleted;
    
}
