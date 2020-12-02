package com.github.camilo.movierental.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.mapper.MovieMapper;
import com.github.camilo.movierental.messages.MovieDto;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.repository.MovieRepository;
import com.github.camilo.movierental.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    
    @Override
    public List<MovieDto> list(Pageable pageable) {
        List<Movie> movies = movieRepository.findAll(pageable).toList();
        return MovieMapper.INSTANCE.map(movies);
    }

    @Override
    public MovieDto create(MovieDto movieDto) {
        Movie movie = MovieMapper.INSTANCE.map(movieDto);
        movie = movieRepository.save(movie);
        return MovieMapper.INSTANCE.map(movie);
    }

    @Override
    public Optional<MovieDto> update(long id, MovieDto movieDto) {
        Optional<Movie> findById = movieRepository.findById(id);
        if (findById.isPresent()) {
            Movie movie = findById.get();
            MovieMapper.INSTANCE.updateMovie(movie, movieDto);
            movie = movieRepository.save(movie);
            return Optional.of(MovieMapper.INSTANCE.map(movie));
        }
        return Optional.empty();//TODO change
    }

    @Override
    public Optional<MovieDto> getById(long id) {
        Optional<Movie> findById = movieRepository.findById(id);
        if (findById.isPresent()) {
            return Optional.of(MovieMapper.INSTANCE.map(findById.get()));
        }
        return Optional.empty();//TODO change
    }

    @Override
    public void delete(long id) {
        movieRepository.deleteById(id);
    }

}
