package com.github.camilo.movierental.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

import com.github.camilo.movierental.messages.MovieDto;
import com.github.camilo.movierental.model.Movie;

class MovieMapperTest {

    private static final String TITTLE = "tittle";
    private static final String DESCRIPTION = "description";
    MovieMapper instance = MovieMapper.INSTANCE;
    
    @Test
    void testMovieToDto() {
        Movie movie = new Movie();
        movie.setAvailability(true);
        movie.setDescription(DESCRIPTION);
        movie.setHistory(Sets.newHashSet());
        movie.setImage(new byte[]{0});
        movie.setIsDeleted(false);
        movie.setLikedUsers(Lists.newArrayList());
        movie.setRentalPrice(new BigDecimal(12));
        movie.setSalePrice(new BigDecimal(7));
        movie.setStock(0);
        movie.setTittle(TITTLE);
        MovieDto map = MovieMapper.INSTANCE.map(movie);
        assertNotNull(map);
        assertNotNull(map.getAvailability());
        assertNotNull(map.getDescription());
        assertEquals(DESCRIPTION, map.getDescription());
        assertNotNull(map.getTittle());
        assertEquals(TITTLE, map.getTittle());
        assertNotNull(map.getIsDeleted());
        assertNotNull(map.getRentalPrice());
        assertNotNull(map.getSalePrice());
    }
    
    @Test
    void testDtoToMovie() {
        MovieDto movie = new MovieDto();
        movie.setAvailability(true);
        movie.setDescription(DESCRIPTION);
        movie.setImage(new byte[]{0});
        movie.setIsDeleted(false);
        movie.setRentalPrice(new BigDecimal(12));
        movie.setSalePrice(new BigDecimal(7));
        movie.setStock(0);
        movie.setTittle(TITTLE);
        Movie map = instance.map(movie);
        assertNotNull(map);
        assertNotNull(map.getAvailability());
        assertNotNull(map.getDescription());
        assertEquals(DESCRIPTION, map.getDescription());
        assertNotNull(map.getTittle());
        assertEquals(TITTLE, map.getTittle());
        assertNotNull(map.getIsDeleted());
        assertNotNull(map.getRentalPrice());
        assertNotNull(map.getSalePrice());
    }

}
