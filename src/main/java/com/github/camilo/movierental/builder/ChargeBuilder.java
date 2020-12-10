package com.github.camilo.movierental.builder;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.github.camilo.movierental.model.Charge;
import com.github.camilo.movierental.model.Movie;
import com.github.camilo.movierental.model.User;

public abstract class ChargeBuilder<T extends Charge> {
    
    protected T charge;
    
    public ChargeBuilder(Class<T> clazz) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        charge = clazz.getDeclaredConstructor().newInstance();
    }
    
    public ChargeBuilder<T> buildEmptyEntity() {
        charge.setDeleted(false);
        charge.setFromDate(OffsetDateTime.now());
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
        charge.setDeleted(isDeleted);
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
