package com.myapp.vod_backend.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
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

    @ElementCollection
    @CollectionTable(name = "tv_show_genres_id")
    private List<Integer> genresId;

    @ManyToOne
    @JoinColumn(name = "watchListId")
    private WatchList watchList;
}
