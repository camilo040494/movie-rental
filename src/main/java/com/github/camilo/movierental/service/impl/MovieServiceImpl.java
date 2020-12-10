package com.github.camilo.movierental.service.impl;

import static com.github.camilo.movierental.exception.ExceptionConstants.MOVIE_NOT_FOUND_EXCEPTION_MESSAGE;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.camilo.movierental.exception.MovieNotFoundException;
import com.github.camilo.movierental.mapper.MovieMapper;
import com.github.camilo.movierental.messages.MovieDto;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.repository.MovieRepository;
import com.github.camilo.movierental.service.MovieService;
import com.github.camilo.movierental.service.chainofresponsabilities.FileValidator;
import com.github.camilo.movierental.util.ConvertFileUtils;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    @Qualifier("fileValidator")
    private FileValidator fileValidator;
    
    @Override
    public List<MovieDto> list(Pageable pageable) {
        List<Movie> movies = movieRepository.findAll(pageable).toList();
        return MovieMapper.INSTANCE.map(movies);
    }

    @Override
    public MovieDto create(MovieDto movieDto) {
        File file = ConvertFileUtils.parseContentToFile(movieDto.getImage());
        fileValidator.validate(file);
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
        throw new MovieNotFoundException(MOVIE_NOT_FOUND_EXCEPTION_MESSAGE);
    }

    @Override
    public Optional<MovieDto> getById(long id) {
        Optional<Movie> findById = movieRepository.findById(id);
        if (findById.isPresent()) {
            return Optional.of(MovieMapper.INSTANCE.map(findById.get()));
        }
        throw new MovieNotFoundException(MOVIE_NOT_FOUND_EXCEPTION_MESSAGE);
    }

    @Override
    public void delete(long id) {
        movieRepository.deleteById(id);
    }

}
