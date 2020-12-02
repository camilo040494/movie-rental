package com.github.camilo.movierental.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.github.camilo.movierental.messages.UserDto;
import com.github.camilo.movierental.model.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    UserDto map(User user);
    
    User map(UserDto userDto);
    
}
