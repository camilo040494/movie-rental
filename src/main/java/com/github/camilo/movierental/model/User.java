package com.github.camilo.movierental.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends BaseEntity {

    private static final long serialVersionUID = -1893059515548293821L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean enabled;
    private boolean tokenExpired;
    
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Charge> history;
    
    public void charge(Charge charge) {
        if (Objects.isNull(history)) {
            history = new HashSet<Charge>();
        }
        charge.setUser(this);
        history.add(charge);
    }
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( 
            name = "user_movies", 
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(
                    name = "movie_id", referencedColumnName = "id")) 
    private List<Movie> likedMovies;
 
    @ManyToMany 
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Collection<Role> roles;
}