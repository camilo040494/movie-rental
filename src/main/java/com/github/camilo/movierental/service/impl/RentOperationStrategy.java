package com.github.camilo.movierental.service.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
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
    
    @Value("#{new java.math.BigDecimal('${movie.rental.penalty.percentage}')}")
    private BigDecimal penaltyPercentagePerDelayedReturn;
    
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
            BigDecimal value = calculateCost(rentedMovie);
            rentRepository.save(rentedMovie);
            return Optional.of(value);
        } else {
            return Optional.empty();
        }
    }
    
    BigDecimal calculateCost(Rent rentedMovie) {
        OffsetDateTime fromDate = rentedMovie.getFromDate();
        BigDecimal cost = rentedMovie.getCost();
        Optional<BigDecimal> penalty = exceededRentingDays(fromDate, cost);
        if (penalty.isPresent()) {
            rentedMovie.setPenalty(penalty.get());
            BigDecimal totalCost = penalty.get().add(rentedMovie.getCost());
            return totalCost;
        }
        return cost;
    }

    Optional<BigDecimal> exceededRentingDays(OffsetDateTime fromDate,
            BigDecimal cost) {
        if (Objects.nonNull(fromDate)) {
            OffsetDateTime untilDate = fromDate.plusDays(daysForRenting);
            OffsetDateTime now = OffsetDateTime.now();
            if (now.isAfter(untilDate)) {
                BigDecimal penalty = calculatePenalty(now, untilDate, cost);
                return Optional.of(penalty);
            }
        }
        return Optional.empty();
    }

    BigDecimal calculatePenalty(OffsetDateTime now, OffsetDateTime untilDate,
            BigDecimal cost) {
        long days = ChronoUnit.WEEKS.between(now, untilDate)+1;
        return new BigDecimal(days).multiply(cost).multiply(penaltyPercentagePerDelayedReturn);
    }

    public void addStock(Movie movie) {
        if (Objects.nonNull(movie)) {
            movie.setStock(movie.getStock()+1);
        }
    }

}
