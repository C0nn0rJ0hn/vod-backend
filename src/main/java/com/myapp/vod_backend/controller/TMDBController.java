package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.tmdb.client.TMDBClient;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/tmdb")
public class TMDBController {

    @Autowired
    private TMDBClient client;

    @GetMapping("/movies/popular/{pageNumber}")
    public List<MovieDto> getPopularMovies(@PathVariable(required = false) String pageNumber) {
        return client.getPopularMovies(pageNumber);
    }

    @GetMapping("/tv/popular/{pageNumber}")
    public List<TvShowDto> getPopularTvShows(@PathVariable(required = false) String pageNumber) {
        return client.getPopularTvShows(pageNumber);
    }

}
