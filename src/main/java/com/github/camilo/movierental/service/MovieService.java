package com.github.camilo.movierental.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.github.camilo.movierental.messages.MovieDto;

public interface MovieService {
    
    List<MovieDto> list(Pageable pageable);
    MovieDto create(MovieDto movieDto);
    Optional<MovieDto> update(MovieDto movieDto);
    MovieDto update(long id, MovieDto movieDto);
    Optional<MovieDto> getById(long id);
    void delete(long id);
    
}
