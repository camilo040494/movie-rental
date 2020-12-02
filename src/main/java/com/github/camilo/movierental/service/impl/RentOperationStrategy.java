package com.github.camilo.movierental.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.builder.RentBuilder;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.model.Rent;
import com.github.camilo.movierental.repository.ChargeRepository;
import com.github.camilo.movierental.service.AbstractChargeOperationStrategy;

@Service
public class RentOperationStrategy extends AbstractChargeOperationStrategy<Rent> {

    @Autowired
    private ChargeRepository<Rent> rentOperation;
    
    @Value("${movie.rental.days.for.renting}")
    private long daysForRenting;
    
    @Override
    protected Rent save(Rent charge) {
        return rentOperation.save(charge);
    }

    @Override
    protected Rent initializeCharge(Movie movie) {
        return new RentBuilder()
                .buildEmptyEntity(daysForRenting)
                .withCost(movie.getSalePrice())
                .build();
    }

}
