package com.github.camilo.movierental.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "charge")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "charge_type")
@Getter
@Setter
public abstract class Charge extends BaseEntity {
    
    private static final long serialVersionUID = 5645216444576497528L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    
    @Column(unique = true)
    protected String transactionId;
    
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    protected User user;
    
    @JoinColumn(name = "movie_id")
    @ManyToOne(fetch = FetchType.LAZY)
    protected Movie movie;
    
    @Column
    protected OffsetDateTime fromDate;
    
    @Column
    protected BigDecimal cost;
    
}