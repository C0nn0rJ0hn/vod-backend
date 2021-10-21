package com.myapp.vod_backend.service;

import com.myapp.vod_backend.tmdb.client.TMDBClient;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TMDBService {

    private final TMDBClient tmdbClient;

    public List<MovieDto> fetchPopularMovies(String page) {
        return tmdbClient.getPopularMovies(page);
    }

    public List<TvShowDto> fetchPopularTvShows(String page) {
        return tmdbClient.getPopularTvShows(page);
    }

    public List<MovieDto> fetchMoviesByQuery(String query, String page) {
        return tmdbClient.searchMoviesByQuery(query, page);
    }

    public List<TvShowDto> fetchTvShowsByQuery(String query, String page) {
        return tmdbClient.searchTvShowsByQuery(query, page);
    }
}
