package com.github.camilo.movierental.service;

import java.util.Optional;

import com.github.camilo.movierental.messages.TransactionDto;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.model.User;

public interface ChargeOperationStrategy {
    
    Optional<TransactionDto> charge(User user, Movie movie);
    
}
