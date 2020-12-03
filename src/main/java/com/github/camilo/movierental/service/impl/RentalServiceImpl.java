package com.github.camilo.movierental.service.impl;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.messages.TransactionDto;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.model.OperationEnum;
import com.github.camilo.movierental.model.Rent;
import com.github.camilo.movierental.model.User;
import com.github.camilo.movierental.repository.ChargeRepository;
import com.github.camilo.movierental.repository.MovieRepository;
import com.github.camilo.movierental.repository.UserRepository;
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
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Optional<TransactionDto> charge(String userEmail, OperationEnum operationEnum,
            long movieId) {
        Optional<User> findByEmail = userRepository.findByEmail(userEmail);
        if (findByEmail.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Optional<Movie> findById = movieRepository.findById(Long.valueOf(movieId));
        if (findById.isPresent()) {
            return chargeOperationStrategy.get(operationEnum)
                    .charge(findByEmail.get(), findById.get());
        }
        //deberia ser una exception TODO
        return Optional.empty();
    }

    @Override
    public Optional<BigDecimal> returnMovie(String transactionId, String userEmail) {
        Optional<Rent> optionalRentedMovie = chargeRepository.getByTransactionId(transactionId);
        if (optionalRentedMovie.isPresent()) {
            Rent rentedMovie = optionalRentedMovie.get();
            rentedMovie.setReturned(true);
            rentedMovie.getMovie().addStock();
            chargeRepository.save(rentedMovie);
            return Optional.ofNullable(rentedMovie.calculateCost());
        } else {            
            return Optional.empty();
        }
    }

}
