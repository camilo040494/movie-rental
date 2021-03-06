package com.github.camilo.movierental.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movie")
@Getter
@Setter
@NoArgsConstructor
public class Movie extends BaseEntity implements Payable {

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
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String image;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable( 
            name = "movie_history", 
            inverseJoinColumns = @JoinColumn(
                    name = "charge_id", referencedColumnName = "id")) 
    private Set<Charge> history;
    
    @ManyToMany
    @JoinTable( 
            name = "movie_users", 
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(
                    name = "movie_id", referencedColumnName = "id"))
    private Set<User> likedUsers;
    
    @Override
    public void charge(Charge charge) {
        if (Objects.isNull(history)) {
            history = new HashSet<Charge>();
        }
        charge.setMovie(this);
        history.add(charge);
    }
    
    public void likeUser(User user) {
        if (Objects.isNull(likedUsers)) {
            likedUsers = new TreeSet<User>();
        }
        likedUsers.add(user);
    }

}