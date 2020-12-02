package com.github.camilo.movierental.builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.github.camilo.movierental.model.Charge;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.model.User;

public abstract class ChargeBuilder<T extends Charge> {
    
    protected T charge;
    private Class<T> clazz;
    
    public ChargeBuilder() {
        try {
            charge = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed the builder");
        }
    }
    
    public ChargeBuilder<T> buildEmptyEntity() {
        charge.setFromDate(null);
        charge.setIsDeleted(false);
        charge.setTransactionId(UUID.randomUUID().toString());
        return this;
    }
    
    public ChargeBuilder<T> withCost(BigDecimal cost) {
        charge.setCost(cost);
        return this;
    }
    
    public ChargeBuilder<T> withFromDate(OffsetDateTime fromDate) {
        charge.setFromDate(fromDate);
        return this;
    }
    
    public ChargeBuilder<T> withIsDeleted(boolean isDeleted) {
        charge.setIsDeleted(isDeleted);
        return this;
    }
    
    public ChargeBuilder<T> withTransactionId(String transactionId) {
        charge.setTransactionId(transactionId);
        return this;
    }
    
    public ChargeBuilder<T> withUser(User user) {
        charge.setUser(user);
        return this;
    }
    
    public ChargeBuilder<T> withMovie(Movie movie) {
        charge.setMovie(movie);
        return this;
    }
    
    public T build() {
        return charge;
    }
    
}