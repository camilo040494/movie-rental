package com.github.camilo.movierental.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Privilege implements Serializable {

    private static final long serialVersionUID = 1262841716665463778L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String name;
 
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}