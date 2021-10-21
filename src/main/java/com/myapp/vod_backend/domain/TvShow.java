package com.myapp.vod_backend.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TV_SHOWS")
public class TvShow {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "popularity")
    private Double popularity;

    @Column(name = "first_air_date")
    private String firstAirDate;

    @Column(name = "vote_average")
    private Double voteAverage;

    @Column(name = "vote_count")
    private Integer voteCount;

    @Column(name = "overview", columnDefinition = "LONGTEXT")
    private String overview;

    @ManyToMany(
            targetEntity = WatchList.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "tvShows"
    )
    private List<WatchList> ListOfWatchLists = new ArrayList<>();

    @ManyToMany(
            targetEntity = Buy.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "tvShows"
    )
    private List<Buy> buys = new ArrayList<>();

    @OneToMany(
            targetEntity = RentTvShow.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "tvShow"
    )
    private List<RentTvShow> rentTvShows = new ArrayList<>();

    public TvShow(Integer id, String name, Double popularity, String firstAirDate, Double voteAverage, Integer voteCount, String overview) {
        this.id = id;
        this.name = name;
        this.popularity = popularity;
        this.firstAirDate = firstAirDate;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.overview = overview;
    }
}
