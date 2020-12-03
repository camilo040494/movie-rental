package com.github.camilo.movierental.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
public class Movie extends BaseEntity {

    private static final long serialVersionUID = 3106606717363470026L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column
    private String tittle;
    @Column
    private String description;
    @Column
    private int stock;
    @Column
    private BigDecimal rentalPrice;
    @Column
    private BigDecimal salePrice;
    @Column
    private Boolean availability;
    @Column
    private byte[] image;
    
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Charge> history;
    
    @ManyToMany
    private List<User> likedUsers;
    
    public void charge(Charge charge) {
        if (Objects.isNull(likedUsers)) {
            likedUsers = new ArrayList<User>();
        }
        charge.setMovie(this);
        history.add(charge);
    }
    
    public void addStock() {
        stock++;
    }
    
    public void substractStock() {
        stock--;
    }
    
}