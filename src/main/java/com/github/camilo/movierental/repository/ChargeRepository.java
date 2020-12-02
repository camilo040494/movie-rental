package com.github.camilo.movierental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.camilo.movierental.model.Charge;

public interface ChargeRepository<T extends Charge> extends JpaRepository<T, Long>{

}
