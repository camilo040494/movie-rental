package com.github.camilo.movierental.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.github.camilo.movierental.messages.UserDto;
import com.github.camilo.movierental.model.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    UserDto map(User user);
    List<UserDto> map(List<User> user);    
    void updateUser(@MappingTarget User user, UserDto userDto);
    
    User map(UserDto userDto);
    
}
