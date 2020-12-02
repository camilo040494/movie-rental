package com.github.camilo.movierental.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.github.camilo.movierental.messages.MovieDto;
import com.github.camilo.movierental.model.Movie;

@Mapper
public interface MovieMapper {
    
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);
    
    MovieDto map(Movie movie);
    
    Movie map(MovieDto movie);
    
}
