package com.myapp.vod_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BUYS")
public class Buy {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "PURCHASED_MOVIES",
            joinColumns = {@JoinColumn(name = "buy_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id", referencedColumnName = "id")}
    )
    private List<Movie> movies = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "PURCHASED_TV_SHOWS",
            joinColumns = {@JoinColumn(name = "buy_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tv_show_id", referencedColumnName = "id")}
    )
    private List<TvShow> tvShows = new ArrayList<>();
}
