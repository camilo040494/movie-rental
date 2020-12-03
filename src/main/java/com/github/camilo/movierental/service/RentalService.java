package com.github.camilo.movierental.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.github.camilo.movierental.messages.TransactionDto;
import com.github.camilo.movierental.model.OperationEnum;

public interface RentalService{
    
    Optional<TransactionDto> charge(String userEmail, OperationEnum operationEnum, long movieId);
    
    Optional<BigDecimal> returnMovie(String transactionId, String userEmail);
    
}