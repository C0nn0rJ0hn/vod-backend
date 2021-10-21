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
@Table(name = "WATCHLISTS")
public class WatchList {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "MOVIES_IN_WATCHLISTS",
            joinColumns = {@JoinColumn(name = "watch_list_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id", referencedColumnName = "id")}
    )
    private List<Movie> movies = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "TV_SHOWS_IN_WATCHLISTS",
            joinColumns = {@JoinColumn(name = "watch_list_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tv_show_id", referencedColumnName = "id")}
    )
    private List<TvShow> tvShows = new ArrayList<>();


}
