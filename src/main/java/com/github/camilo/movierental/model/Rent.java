package com.github.camilo.movierental.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.data.relational.core.mapping.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rent extends Charge {
    
    @Column
    private OffsetDateTime untilDate;
    @Column
    private BigDecimal penalty;
    
}