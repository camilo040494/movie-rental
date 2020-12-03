package com.github.camilo.movierental.messages;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDto implements Serializable {
    
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    
}