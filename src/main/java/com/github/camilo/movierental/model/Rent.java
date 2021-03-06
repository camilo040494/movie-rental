package com.github.camilo.movierental.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "rent")
@DiscriminatorValue(value = "RENT")
public class Rent extends Charge {
    
    private static final long serialVersionUID = 3521819851810183806L;

    @Column
    @NotNull
    private OffsetDateTime untilDate;
    
    @Column
    private BigDecimal penalty;
    
    @Column
    private Boolean returned;
    
}