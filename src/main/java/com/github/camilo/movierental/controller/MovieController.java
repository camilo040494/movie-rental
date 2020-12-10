package com.github.camilo.movierental.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.camilo.movierental.messages.MovieDto;
import com.github.camilo.movierental.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;
    
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<MovieDto> get(@PathVariable long id){
        Optional<MovieDto> movie = movieService.getById(id);
        if (movie.isPresent()) {
            return new ResponseEntity<MovieDto>(movie.get(), HttpStatus.OK);
        }
        return new ResponseEntity<MovieDto>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<MovieDto>> list(Pageable pageable){
        List<MovieDto> list = movieService.list(pageable);
        if (CollectionUtils.isNotEmpty(list)) {
            return new ResponseEntity<List<MovieDto>>(list, HttpStatus.OK);
        } else
            return new ResponseEntity<List<MovieDto>>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping
    @ResponseBody
    public ResponseEntity<MovieDto> create(@RequestBody MovieDto movieDto){
        MovieDto create = movieService.create(movieDto);
        return new ResponseEntity<MovieDto>(create, HttpStatus.CREATED);
    }
    
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<MovieDto> update(@PathVariable long id, @RequestBody MovieDto movieDto){
        Optional<MovieDto> updated = movieService.update(id, movieDto);
        if (updated.isPresent()) {
            return new ResponseEntity<MovieDto>(updated.get(), HttpStatus.OK);
        }
        return new ResponseEntity<MovieDto>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        movieService.delete(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }
}
