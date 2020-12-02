package com.github.camilo.movierental.service;

import com.github.camilo.movierental.model.OperationEnum;

public interface RentalService{
    
    void charge(OperationEnum operationEnum, long movieId);
    
}