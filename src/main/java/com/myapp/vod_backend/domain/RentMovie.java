package com.myapp.vod_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RENT_MOVIES")
public class RentMovie {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "rent_date")
    private String rentDate;

    @Column(name = "expire_date")
    private String expireDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public RentMovie(Account account, Movie movie) {
        this.account = account;
        this.movie = movie;
    }
}
