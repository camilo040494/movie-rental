package com.github.camilo.movierental.service;

import java.math.BigDecimal;
import java.util.Optional;

public interface RentOperationService {
    
    Optional<BigDecimal> returnMovie(String transactionId, String email);
    
}
