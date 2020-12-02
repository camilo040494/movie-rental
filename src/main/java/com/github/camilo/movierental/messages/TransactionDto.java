package com.github.camilo.movierental.messages;

import java.io.Serializable;

import lombok.Data;

@Data
public class TransactionDto implements Serializable {
    
    private MovieDto movie;
    private String transactionId;
    
}
