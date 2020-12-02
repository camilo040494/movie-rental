package com.github.camilo.movierental.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.github.camilo.movierental.model.Movie;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long>{
    
}
