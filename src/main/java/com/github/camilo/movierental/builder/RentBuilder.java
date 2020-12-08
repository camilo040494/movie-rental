package com.github.camilo.movierental.builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.github.camilo.movierental.model.Rent;

public class RentBuilder extends ChargeBuilder<Rent>{

    public RentBuilder() {
        super(Rent.class);
    }
    
    public RentBuilder withUntilDate(OffsetDateTime untilDate) {
        charge.setUntilDate(untilDate);
        return this;
    }
    
    public RentBuilder withPenalty(BigDecimal penalty) {
        charge.setPenalty(penalty);
        return this;
    }

    public ChargeBuilder<Rent> buildEmptyEntity(long daysForRenting) {
        super.buildEmptyEntity();
        OffsetDateTime fromDate = charge.getFromDate();
        charge.setUntilDate(fromDate.plusDays(daysForRenting));
        charge.setPenalty(new BigDecimal(0));
        charge.setReturned(false);
        return this;
    }
    
}
