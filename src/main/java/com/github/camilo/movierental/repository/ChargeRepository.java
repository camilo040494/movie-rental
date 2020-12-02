package com.github.camilo.movierental.repository;

import org.springframework.data.repository.CrudRepository;

import com.github.camilo.movierental.model.Charge;

public interface ChargeRepository<T extends Charge> extends CrudRepository<T, Long>{

}
