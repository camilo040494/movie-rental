package com.github.camilo.movierental.service.impl;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.builder.RentBuilder;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.model.Rent;
import com.github.camilo.movierental.repository.ChargeRepository;
import com.github.camilo.movierental.service.AbstractChargeOperationStrategy;
import com.github.camilo.movierental.service.RentOperationService;

@Service
public class RentOperationStrategy extends AbstractChargeOperationStrategy<Rent> implements RentOperationService{

    @Autowired
    private ChargeRepository<Rent> rentRepository;
    
    @Value("${movie.rental.days.for.renting}")
    private long daysForRenting;
    
    @Override
    protected Rent save(Rent charge) {
        return rentRepository.save(charge);
    }

    @Override
    protected Rent initializeCharge(Movie movie) {
        return new RentBuilder()
                .buildEmptyEntity(daysForRenting)
                .withCost(movie.getRentalPrice())
                .build();
    }

    @Override
    public Optional<BigDecimal> returnMovie(String transactionId, String email) {
        Optional<Rent> optionalRentedMovie = rentRepository.getByTransactionId(transactionId);
        if (optionalRentedMovie.isPresent()) {
            Rent rentedMovie = optionalRentedMovie.get();
            rentedMovie.setReturned(true);
            addStock(rentedMovie.getMovie());
            rentRepository.save(rentedMovie);
            return rentedMovie.calculateCost();
        } else {
            return Optional.empty();
        }
    }
    
    public void addStock(Movie movie) {
        if (Objects.nonNull(movie)) {
            movie.setStock(movie.getStock()+1));
        }
    }

}
