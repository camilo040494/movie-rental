package com.github.camilo.movierental.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.github.camilo.movierental.messages.MovieDto;
import com.github.camilo.movierental.model.Movie;

@Mapper
public interface MovieMapper {
    
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);
    
    MovieDto map(Movie movie);
    
    @Mapping(target = "id", ignore=true)
    Movie map(MovieDto movie);
    
}
