package com.myapp.vod_backend.tmdb.facade;

import com.myapp.vod_backend.mapper.TMDBMapper;
import com.myapp.vod_backend.service.TMDBService;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TMDBFacade {

    @Autowired
    private TMDBMapper mapper;

    @Autowired
    private TMDBService service;

    public List<MovieDto> fetchPopularMovies(String page) {
        return mapper.mapToMoviesDto(mapper.mapToMovies(service.fetchPopularMovies(page)));
    }

    public List<MovieDto> fetchMoviesByQuery(String query, String page) {
        return mapper.mapToMoviesDto(mapper.mapToMovies(service.fetchMoviesByQuery(query, page)));
    }

    public List<TvShowDto> fetchPopularTvShows(String page) {
        return mapper.mapToTvShowsDto(mapper.mapToTvShows(service.fetchPopularTvShows(page)));
    }

    public List<TvShowDto> fetchTvShowsByQuery(String query, String page) {
        return mapper.mapToTvShowsDto(mapper.mapToTvShows(service.fetchTvShowsByQuery(query, page)));
    }
}
