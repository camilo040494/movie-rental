package com.github.camilo.movierental.service.impl;

import static com.github.camilo.movierental.exception.ExceptionConstants.MOVIE_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.github.camilo.movierental.exception.ExceptionConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.exception.MovieNotFoundException;
import com.github.camilo.movierental.exception.UserNotFoundException;
import com.github.camilo.movierental.messages.TransactionDto;
import com.github.camilo.movierental.model.Charge;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.model.OperationEnum;
import com.github.camilo.movierental.model.User;
import com.github.camilo.movierental.repository.ChargeRepository;
import com.github.camilo.movierental.repository.MovieRepository;
import com.github.camilo.movierental.repository.UserRepository;
import com.github.camilo.movierental.service.RentOperationService;
import com.github.camilo.movierental.service.RentalService;
import com.github.camilo.movierental.service.strategypattern.ChargeOperationStrategy;

@Service
public class RentalServiceImpl implements RentalService {

    

    @Autowired
    private ChargeRepository<Charge> chargeRepository;
    
    @Autowired
    private RentOperationService rentOperationService;
    
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
            throw new UserNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        Optional<Movie> findById = movieRepository.findById(Long.valueOf(movieId));
        if (findById.isPresent()) {
            return chargeOperationStrategy.get(operationEnum)
                    .charge(findByEmail.get(), findById.get());
        }
        throw new MovieNotFoundException(MOVIE_NOT_FOUND_EXCEPTION_MESSAGE);
    }

    @Override
    public Optional<BigDecimal> returnMovie(String transactionId, String userEmail) {
        return rentOperationService.returnMovie(transactionId, userEmail);
    }

    @Override
    public BigDecimal calculateProfits() {
        BigDecimal profits = new BigDecimal(0);
        Iterable<Charge> findAll = chargeRepository.findAll();
        for (Charge profit : findAll) {
            Optional<BigDecimal> calculateCost = Optional.of(profit.getCost());
            if(calculateCost.isPresent()) {
                profits = profits.add(calculateCost.get());
            }
        }
        return profits;
    }

}
