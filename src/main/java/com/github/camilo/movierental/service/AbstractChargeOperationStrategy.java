package com.github.camilo.movierental.service;

import java.util.Optional;

import com.github.camilo.movierental.mapper.MovieMapper;
import com.github.camilo.movierental.messages.TransactionDto;
import com.github.camilo.movierental.model.Charge;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.model.User;

public abstract class AbstractChargeOperationStrategy<T extends Charge> implements ChargeOperationStrategy {

    @Override
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
            movie.substractStock();
            if (movie.getStock()==0) {
                movie.setAvailability(false);
            }
        } else {
            throw new RuntimeException("Movie not available at the time");
        }
    }

    protected abstract T save(T charge);

    protected abstract T initializeCharge(Movie movie);

}
