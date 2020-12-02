package com.github.camilo.movierental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.camilo.movierental.model.OperationEnum;
import com.github.camilo.movierental.service.RentalService;

@RestController
@RequestMapping("/charge")
public class RentalController{
    
    @Autowired
    private RentalService rentalService;
    
    @PostMapping(value = "/rent/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> rentMovie(@PathVariable long movieId){
        rentalService.charge(OperationEnum.RENT, movieId);
        return new ResponseEntity<Boolean>(HttpStatus.OK);
    }
    
    @PostMapping(value = "/buy/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> buyMovie(@PathVariable long movieId){
        rentalService.charge(OperationEnum.BUY, movieId);
        return new ResponseEntity<Boolean>(HttpStatus.OK);
    }
    
}