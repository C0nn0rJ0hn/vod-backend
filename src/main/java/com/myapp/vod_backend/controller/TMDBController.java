package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import com.myapp.vod_backend.tmdb.facade.TMDBFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/tmdb")
public class TMDBController {

    @Autowired
    private TMDBFacade facade;

    @GetMapping("/movies/popular/{pageNumber}")
    public List<MovieDto> getPopularMovies(@PathVariable(required = false) String pageNumber) {
        return facade.fetchPopularMovies(pageNumber);
    }

    @GetMapping("/tv/popular/{pageNumber}")
    public List<TvShowDto> getPopularTvShows(@PathVariable(required = false) String pageNumber) {
        return facade.fetchPopularTvShows(pageNumber);
    }

    @GetMapping(value = "/movies/search")
    public List<MovieDto> searchMoviesByQuery(@RequestParam String query, @RequestParam(required = false) String page) {
        return facade.fetchMoviesByQuery(query, page);
    }

    @GetMapping(value = "/tv/search")
    public List<TvShowDto> searchTvShowsByQuery(@RequestParam String query, @RequestParam(required = false) String page) {
        return facade.fetchTvShowsByQuery(query, page);
    }
}
