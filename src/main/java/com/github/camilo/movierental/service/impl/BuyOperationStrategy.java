package com.github.camilo.movierental.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.builder.BuyBuilder;
import com.github.camilo.movierental.model.Buy;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.repository.ChargeRepository;
import com.github.camilo.movierental.service.strategypattern.AbstractChargeOperationStrategy;

@Service
public class BuyOperationStrategy extends AbstractChargeOperationStrategy<Buy> {

    @Autowired
    private ChargeRepository<Buy> buyRepository;

    @Override
    protected Buy save(Buy charge) {
        return buyRepository.save(charge);
    }
    
    @Override
    protected Buy initializeCharge(Movie movie) {
        return new BuyBuilder().buildEmptyEntity()
                .withCost(movie.getSalePrice())
                .build();
    }
    
}
