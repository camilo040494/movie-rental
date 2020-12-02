package com.github.camilo.movierental.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.camilo.movierental.messages.TransactionDto;
import com.github.camilo.movierental.model.OperationEnum;
import com.github.camilo.movierental.service.RentalService;

@RestController
@RequestMapping("/charge")
public class RentalController{
    
    @Autowired
    private RentalService rentalService;
    
    @PostMapping(value = "/rent/{movieId}")
    @ResponseBody
    public ResponseEntity<TransactionDto> rentMovie(@PathVariable long movieId){
        Optional<TransactionDto> transaction = rentalService.charge(OperationEnum.RENT, movieId);
        if (transaction.isPresent()) {
            return new ResponseEntity<TransactionDto>(transaction.get(), HttpStatus.OK);
        }
        return new ResponseEntity<TransactionDto>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping(value = "/return/{transactionId}")
    @ResponseBody
    public ResponseEntity<BigDecimal> returnMovie(@PathVariable String transactionId){
        Optional<BigDecimal> charge = rentalService.returnMovie(transactionId);
		if (charge.isPresent()) {
            return new ResponseEntity<BigDecimal>(charge.get(), HttpStatus.OK);
        }
        return new ResponseEntity<BigDecimal>(HttpStatus.OK);
    }
    
    @PostMapping(value = "/buy/{movieId}")
    @ResponseBody
    public ResponseEntity<TransactionDto> buyMovie(@PathVariable long movieId){
        Optional<TransactionDto> transaction = rentalService.charge(OperationEnum.BUY, movieId);
        if (transaction.isPresent()) {
            return new ResponseEntity<TransactionDto>(transaction.get(), HttpStatus.OK);
        }
        return new ResponseEntity<TransactionDto>(HttpStatus.NOT_FOUND);
    }
    
}