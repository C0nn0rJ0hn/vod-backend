package com.myapp.vod_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "country")
    private String country;

    @Column(name = "language")
    private String language;

    @Column(name = "role")
    private Role role;

    @Column(name = "logged_in")
    private boolean loggedIn;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "watchlist_id")
    private WatchList watchList;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "buy_id")
    private Buy buy;

    @OneToMany(
            targetEntity = RentMovie.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "account"
    )
    private List<RentMovie> rentMovies = new ArrayList<>();

    @OneToMany(
            targetEntity = RentTvShow.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "account"
    )
    private List<RentTvShow> rentTvShows = new ArrayList<>();

    public Account(User user) {
        this.user = user;
    }

    public Account(String createdAt, String updatedAt, User user) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }

    public Account(String password, String email, String country, String language, Role role, User user) {
        this.password = password;
        this.email = email;
        this.country = country;
        this.language = language;
        this.role = role;
        this.user = user;
    }

    public Account(Integer id, String password, String email, String country, String language, Role role, boolean loggedIn, User user) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.country = country;
        this.language = language;
        this.role = role;
        this.loggedIn = loggedIn;
        this.user = user;
    }
}
