package com.github.camilo.movierental.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.github.camilo.movierental.messages.MovieDto;
import com.github.camilo.movierental.model.Movie;

@Mapper
public interface MovieMapper {
    
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);
    
    MovieDto map(Movie movie);
    List<MovieDto> map(List<Movie> movie);
    void updateMovie(@MappingTarget Movie movie, MovieDto movieDto);
    
    Movie map(MovieDto movie);
    
}
