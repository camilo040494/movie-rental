package com.github.camilo.movierental.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.github.camilo.movierental.exception.NoStockException;
import com.github.camilo.movierental.mapper.MovieMapper;
import com.github.camilo.movierental.messages.TransactionDto;
import com.github.camilo.movierental.model.Charge;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.model.User;

public abstract class AbstractChargeOperationStrategy<T extends Charge> implements ChargeOperationStrategy {

    @Override
    @Transactional
    public Optional<TransactionDto> charge(User user, Movie movie) {
        validateMovie(movie);
        
        T charge = initializeCharge(movie);
        
        movie.charge(charge);
        user.charge(charge);
        
        charge = save(charge);
        
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionId(charge.getTransactionId())
                .movie(MovieMapper.INSTANCE.map(charge.getMovie())).build();
        return Optional.of(transactionDto);
    }

    private void validateMovie(Movie movie) {
        if (movie.getAvailability()) {
            subtractStock(movie);
            if (movie.getStock()==0) {
                movie.setAvailability(false);
            }
        } else {
            throw new NoStockException("Movie not available at the time");
        }
    }
    public void subtractStock(Movie movie) {
        movie.setStock(movie.getStock()-1);
    }
    protected abstract T save(T charge);

    protected abstract T initializeCharge(Movie movie);

}
