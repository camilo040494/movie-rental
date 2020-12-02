package com.github.camilo.movierental.service;

import java.util.Optional;

import com.github.camilo.movierental.messages.ChargeDto;
import com.github.camilo.movierental.model.OperationEnum;

public interface RentalService{
    
    Optional<ChargeDto> charge(OperationEnum operationEnum, long movieId);
    
}