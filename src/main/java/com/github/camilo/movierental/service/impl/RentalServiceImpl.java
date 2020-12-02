package com.github.camilo.movierental.service.impl;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.messages.TransactionDto;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.model.OperationEnum;
import com.github.camilo.movierental.model.Rent;
import com.github.camilo.movierental.repository.ChargeRepository;
import com.github.camilo.movierental.repository.MovieRepository;
import com.github.camilo.movierental.service.ChargeOperationStrategy;
import com.github.camilo.movierental.service.RentalService;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private ChargeRepository<Rent> chargeRepository;
    
    @Autowired
    private EnumMap<OperationEnum, ChargeOperationStrategy> chargeOperationStrategy;
    
    @Autowired
    private MovieRepository movieRepository;
    
    @Override
    public Optional<TransactionDto> charge(OperationEnum operationEnum, long movieId) {
        Optional<Movie> findById = movieRepository.findById(Long.valueOf(movieId));
        if (findById.isPresent()) {
            //TODO retreive the user
            return chargeOperationStrategy.get(operationEnum).charge(null, findById.get());
        }
        //deberia ser una exception TODO
        return Optional.empty();
    }

    @Override
    public Optional<BigDecimal> returnMovie(String transactionId) {
        Optional<Rent> optionalRentedMovie = chargeRepository.getByTransactionId(transactionId);
        if (optionalRentedMovie.isPresent()) {
            Rent rentedMovie = optionalRentedMovie.get();
            BigDecimal penalty = rentedMovie.getPenalty();
            BigDecimal cost = rentedMovie.getCost();
            if (Objects.isNull(penalty)) {
                return Optional.ofNullable(cost);
            } else {
                return Optional.of(cost.add(penalty));
            }
        } else {            
            return Optional.empty();
        }
    }

}
