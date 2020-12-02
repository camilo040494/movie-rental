package com.github.camilo.movierental.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.camilo.movierental.messages.ChargeDto;
import com.github.camilo.movierental.model.OperationEnum;
import com.github.camilo.movierental.service.RentalService;

@RestController
@RequestMapping("/rent")
public class RentalController{
    
    @Autowired
    private RentalService rentService;
    
    @PostMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ChargeDto> rentMovie(@PathVariable long movieId){
        Optional<ChargeDto> charge = rentService.charge(OperationEnum.RENT, movieId);
        if (charge.isPresent()) {
            return new ResponseEntity<ChargeDto>(charge.get(), HttpStatus.OK);
        }
        return new ResponseEntity<ChargeDto>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @PostMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<ChargeDto> buyMovie(@PathVariable long movieId){
        Optional<ChargeDto> charge = rentService.charge(OperationEnum.BUY, movieId);
        if (charge.isPresent()) {
            return new ResponseEntity<ChargeDto>(charge.get(), HttpStatus.OK);
        }
        return new ResponseEntity<ChargeDto>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}