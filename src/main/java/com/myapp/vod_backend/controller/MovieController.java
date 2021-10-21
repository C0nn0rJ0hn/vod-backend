package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.exception.MovieNotFoundException;
import com.myapp.vod_backend.mapper.MovieMapper;
import com.myapp.vod_backend.service.MovieService;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class MovieController {

    @Autowired
    private MovieMapper mapper;

    @Autowired
    private MovieService service;

    @GetMapping(value = "/movies")
    public List<MovieDto> getMovies() {
        return mapper.mapToListMovieDto(service.getMovies());
    }

    @GetMapping(value = "/movies/{movieId}")
    public MovieDto getMovie(@PathVariable Integer movieId) {
        return mapper.mapToMovieDto(service.getMovieById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found")));
    }

    @GetMapping(value = "/movies/searchByRatings")
    public List<MovieDto> getMoviesWithAverageAbove(@RequestParam Double average) {
        return mapper.mapToListMovieDto(service.getMoviesWithAverageAbove(average));
    }

    @GetMapping(value = "/movies/searchByKeyword")
    public List<MovieDto> getMoviesByKeyword(@RequestParam String keyword) {
        return mapper.mapToListMovieDto(service.getMoviesByKeyword(keyword));
    }


}
