package com.myapp.vod_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MOVIES")
public class Movie {
    @Column(name = "title")
    private String title;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "popularity")
    private Double popularity;

    @Column(name = "vote_count")
    private Integer voteCount;

    @Column(name = "vote_average")
    private Double voteAverage;

    @Column(name = "overview", columnDefinition = "LONGTEXT")
    private String overview;

    @ManyToMany(
            targetEntity = WatchList.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "movies"
    )
    private List<WatchList> listOfWatchLists = new ArrayList<>();

    @ManyToMany(
            targetEntity = Buy.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "movies"
    )
    private List<Buy> buys = new ArrayList<>();

    @OneToMany(
            targetEntity = RentMovie.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "movie"
    )
    private List<RentMovie> rentMovies = new ArrayList<>();


    public Movie(String title, Integer id, String releaseDate, Double popularity, Integer voteCount, Double voteAverage, String overview) {
        this.title = title;
        this.id = id;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }
}
