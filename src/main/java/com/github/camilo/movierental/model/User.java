package com.github.camilo.movierental.model;

import java.util.Collection;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "application_user")
@Getter
@Setter
public class User extends BaseEntity {

    private static final long serialVersionUID = -1893059515548293821L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String firstName;
    
    @Column
    private String lastName;
    @Column(unique = true)
    private String email;
    
    @Column
    private String password;
    @Column
    private boolean enabled;
    @Column
    private boolean tokenExpired;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_history") 
    private Set<Charge> history;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( 
            name = "user_liked_movies", 
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(
                    name = "movie_id", referencedColumnName = "id")) 
    private Set<Movie> likedMovies;
 
    @ManyToMany 
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Collection<Role> roles;
    
    public void charge(Charge charge) {
        if (Objects.isNull(history)) {
            history = new HashSet<Charge>();
        }
        charge.setUser(this);
        history.add(charge);
    }
    
    public void likeMovie (Movie movie) {
        if (Objects.isNull(likedMovies)) {
            likedMovies = new TreeSet<Movie>();
        }
        likedMovies.add(movie);
    }
}