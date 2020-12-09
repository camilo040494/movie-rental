package com.github.camilo.movierental.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.exception.ExceptionConstants;
import com.github.camilo.movierental.exception.UserNotFoundException;
import com.github.camilo.movierental.model.Role;
import com.github.camilo.movierental.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.github.camilo.movierental.model.User appUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND_EXCEPTION_MESSAGE));

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        for (Role authority : appUser.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName().name());
            grantList.add(grantedAuthority);
        }

        UserDetails user = (UserDetails) new User(appUser.getEmail(), appUser.getPassword(), grantList);
        return user;
    }

}
