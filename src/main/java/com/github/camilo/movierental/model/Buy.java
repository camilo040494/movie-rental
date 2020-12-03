package com.github.camilo.movierental.model;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "buy")
@DiscriminatorValue(value = "BUY")
public class Buy extends Charge {

    private static final long serialVersionUID = 7711705029444141443L;

    @Override
    public Optional<BigDecimal> calculateCost() {
        return Optional.of(cost);
    }
    
}