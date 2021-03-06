package com.github.camilo.movierental.messages;

import java.io.Serializable;
import java.math.BigDecimal;


import lombok.Data;

@Data
public class MovieDto implements Serializable{
    
    private Long id;
    private String tittle;
    private String description;
    private int stock;
    private BigDecimal rentalPrice;
    private BigDecimal salePrice;
    private Boolean availability;
    private String image;
    private Boolean isDeleted;
    
}
