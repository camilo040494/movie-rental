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
@RequestMapping("/rent")
public class RentalController{
    
    @Autowired
    private RentalService rentService;
    
    @PostMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> rentMovie(@PathVariable long movieId){
        rentService.charge(OperationEnum.RENT, movieId);
        return new ResponseEntity<Boolean>(HttpStatus.OK);
    }
    
    @PostMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> buyMovie(@PathVariable long movieId){
        rentService.charge(OperationEnum.BUY, movieId);
        return new ResponseEntity<Boolean>(HttpStatus.OK);
    }
    
}