package com.myapp.vod_backend.tmdb.client;

import com.myapp.vod_backend.tmdb.config.TMDBConfig;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.movie.dto.PopularMoviesDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.PopularTvShowsDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TMDBClient {

    private final TMDBConfig tmdbConfig;
    private final RestTemplate restTemplate;

    private Integer moviesTotalPages;
    private Integer tvShowsTotalPages;

    private static final Logger LOGGER = LoggerFactory.getLogger(TMDBClient.class);

    public List<MovieDto> getPopularMovies(String pageNumber) {
        URI url = UriComponentsBuilder.fromHttpUrl(tmdbConfig.getTmdbApiEndpoint() + "/movie/popular")
                .queryParam("api_key", tmdbConfig.getTmdbApiKey())
                .queryParam("page", pageNumber)
                .build()
                .encode()
                .toUri();
        try {
            PopularMoviesDto apiResponse = restTemplate.getForObject(url, PopularMoviesDto.class);
            moviesTotalPages = Optional.ofNullable(apiResponse).map(PopularMoviesDto::getTotalPages).orElse(null);
            return Optional.ofNullable(apiResponse)
                    .map(PopularMoviesDto::getResults)
                    .orElse(Collections.emptyList());
        }
        catch (RestClientException e){
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Integer getMoviesTotalPages() {
        return moviesTotalPages;
    }

    public List<TvShowDto> getPopularTvShows(String pageNumber) {
        URI url = UriComponentsBuilder.fromHttpUrl(tmdbConfig.getTmdbApiEndpoint() + "/tv/popular")
                .queryParam("api_key", tmdbConfig.getTmdbApiKey())
                .queryParam("page", pageNumber)
                .build()
                .encode()
                .toUri();

        try {
            PopularTvShowsDto apiResponse = restTemplate.getForObject(url, PopularTvShowsDto.class);
            tvShowsTotalPages = Optional.ofNullable(apiResponse).map(PopularTvShowsDto::getTotalPages).orElse(null);
            return Optional.ofNullable(apiResponse)
                    .map(PopularTvShowsDto::getResults)
                    .orElse(Collections.emptyList());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Integer getTvShowsTotalPages() {
        return tvShowsTotalPages;
    }


    public List<MovieDto> searchMoviesByQuery(String query, String page) {
        URI url = UriComponentsBuilder.fromHttpUrl(tmdbConfig.getTmdbApiEndpoint() + "/search/movie")
                .queryParam("api_key", tmdbConfig.getTmdbApiKey())
                .queryParam("query", query)
                .queryParam("page", page)
                .build()
                .encode()
                .toUri();

        try {
            PopularMoviesDto apiResponse = restTemplate.getForObject(url, PopularMoviesDto.class);
            return Optional.ofNullable(apiResponse).map(PopularMoviesDto::getResults).orElse(Collections.emptyList());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<TvShowDto> searchTvShowsByQuery(String query, String page) {
        URI url = UriComponentsBuilder.fromHttpUrl(tmdbConfig.getTmdbApiEndpoint() + "/search/tv")
                .queryParam("api_key", tmdbConfig.getTmdbApiKey())
                .queryParam("query", query)
                .queryParam("page", page)
                .build()
                .encode()
                .toUri();

        try {
            PopularTvShowsDto apiResponse = restTemplate.getForObject(url, PopularTvShowsDto.class);
            return Optional.ofNullable(apiResponse).map(PopularTvShowsDto::getResults).orElse(Collections.emptyList());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

}
