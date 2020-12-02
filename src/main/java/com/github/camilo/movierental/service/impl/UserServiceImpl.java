package com.github.camilo.movierental.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.mapper.UserMapper;
import com.github.camilo.movierental.messages.UserDto;
import com.github.camilo.movierental.model.User;
import com.github.camilo.movierental.repository.UserRepository;
import com.github.camilo.movierental.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<UserDto> list(Pageable pageable) {
        List<User> users = userRepository.findAll(pageable).toList();
        return UserMapper.INSTANCE.map(users);
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = UserMapper.INSTANCE.map(userDto);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.map(user);
    }

    @Override
    public Optional<UserDto> update(long id, UserDto userDto) {
        Optional<User> findById = userRepository.findById(id);
        if (findById.isPresent()) {
            User user = findById.get();
            UserMapper.INSTANCE.updateUser(user, userDto);
            user = userRepository.save(user);
            return Optional.of(UserMapper.INSTANCE.map(user));
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> getById(long id) {
        Optional<User> findById = userRepository.findById(id);
        if (findById.isPresent()) {
            return Optional.of(UserMapper.INSTANCE.map(findById.get()));            
        }
        return Optional.empty();
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

}
