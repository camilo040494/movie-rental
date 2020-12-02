package com.github.camilo.movierental.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.camilo.movierental.messages.UserDto;
import com.github.camilo.movierental.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<UserDto> get(@PathVariable long id){
        Optional<UserDto> user = userService.getById(id);
        if (user.isPresent()) {
            return new ResponseEntity<UserDto>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping
    @ResponseBody
    public ResponseEntity<UserDto> list(){
        List<UserDto> list = userService.list();
        if (CollectionUtils.isNotEmpty(list)) {
            return null;
        } else
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping
    @ResponseBody
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto){
        UserDto create = userService.create(userDto);
        return new ResponseEntity<UserDto>(create, HttpStatus.CREATED);
    }
    
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<UserDto> update(@PathVariable long id, @RequestBody UserDto userDto){
        Optional<UserDto> updated = userService.update(userDto);
        if (updated.isPresent()) {
            return new ResponseEntity<UserDto>(updated.get(), HttpStatus.OK);
        }
        return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        userService.delete(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
    
}