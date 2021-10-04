package com.myapp.vod_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
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

    @ElementCollection
    @CollectionTable(name = "movies_genres_id")
    private List<Integer> genresId;

    @ManyToOne
    @JoinColumn(name = "watchListId")
    private WatchList watchList;

   /* private String originalTitle;
    private boolean adult;
    private String overview;
    private String originalLanguage;
    private String posterPath;
    private String backdropPath;
    private boolean video;*/
}
