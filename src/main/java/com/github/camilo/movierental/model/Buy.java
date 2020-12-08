package com.github.camilo.movierental.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "buy")
@DiscriminatorValue(value = "BUY")
public class Buy extends Charge {

    private static final long serialVersionUID = 7711705029444141443L;
    
}