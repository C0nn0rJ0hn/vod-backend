package com.myapp.vod_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "WATCHLISTS")
public class WatchList {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "media_type", columnDefinition = "enum('MOVIE', 'TVSHOW')")
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @OneToOne(fetch = FetchType.EAGER)
    @Column(name = "account_id")
    private Account account;

    @OneToMany(
            targetEntity = Movie.class,
            mappedBy = "watchList",
            fetch = FetchType.LAZY)
    private List<Movie> movies;

    @OneToMany(
            targetEntity = TvShow.class,
            mappedBy = "watchList",
            fetch = FetchType.LAZY
    )
    private List<TvShow> tvShows;
}
