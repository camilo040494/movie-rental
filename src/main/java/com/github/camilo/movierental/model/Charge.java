package com.github.camilo.movierental.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "charge_type")
@Getter
@Setter
public class Charge implements Serializable {
    
    @Id
    @Column
    private long id;
    
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    
    @Column
    private OffsetDateTime fromDate;
    
    @Column
    private BigDecimal cost;
    
}