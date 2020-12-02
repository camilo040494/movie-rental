package com.github.camilo.movierental.messages;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto implements Serializable {
    
    private MovieDto movie;
    private String transactionId;
    
}
