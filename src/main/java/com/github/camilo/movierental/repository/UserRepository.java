package com.github.camilo.movierental.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.github.camilo.movierental.model.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>{
    
    
    
}
