package com.github.camilo.movierental.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.github.camilo.movierental.messages.UserDto;

public interface UserService{
    
    List<UserDto> list(Pageable pageable);
    UserDto create(UserDto userDto);
    Optional<UserDto> update(long id, UserDto userDto);
    Optional<UserDto> getById(long id);
    void delete(long id);
    
}