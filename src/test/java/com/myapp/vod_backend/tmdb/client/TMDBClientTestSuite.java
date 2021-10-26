package com.myapp.vod_backend.tmdb.client;

import com.myapp.vod_backend.tmdb.config.TMDBConfig;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.movie.dto.PopularMoviesDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.PopularTvShowsDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TMDBClientTestSuite {

    @InjectMocks
    private TMDBClient tmdbClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TMDBConfig tmdbConfig;

    @Test
    public void shouldFetchPopularMovies() throws URISyntaxException {
        //Given
        when(tmdbConfig.getTmdbApiEndpoint()).thenReturn("http://test.com");
        when(tmdbConfig.getTmdbApiKey()).thenReturn("test");

        MovieDto[] movies = new MovieDto[1];
        movies[0] = new MovieDto("Title", 1, "Release Date", 200, 100, 8.8, "Overview");

        PopularMoviesDto popular = new PopularMoviesDto(1, Arrays.asList(movies), 10, 1);

        URI url = new URI("http://test.com/movie/popular?api_key=test&page=1");

        when(restTemplate.getForObject(url, PopularMoviesDto.class)).thenReturn(popular);

        //When
        List<MovieDto> fetchedMovies = tmdbClient.getPopularMovies("1");

        //Then
        Assertions.assertEquals(1, fetchedMovies.size());
        Assertions.assertEquals(1, fetchedMovies.get(0).getId());
    }

    @Test
    public void shouldFetchPopularTvShows() throws URISyntaxException {
        //Given
        when(tmdbConfig.getTmdbApiEndpoint()).thenReturn("http://test.com");
        when(tmdbConfig.getTmdbApiKey()).thenReturn("test");

        TvShowDto[] tvShows = new TvShowDto[1];
        tvShows[0] = new TvShowDto(2, "Name", 300, "First air date", 7.6, 500, "Overview");
        PopularTvShowsDto popular = new PopularTvShowsDto(1, Arrays.asList(tvShows), 10, 1);

        URI url = new URI("http://test.com/tv/popular?api_key=test&page=1");

        when(restTemplate.getForObject(url, PopularTvShowsDto.class)).thenReturn(popular);

        //When
        List<TvShowDto> fetchedTvShows = tmdbClient.getPopularTvShows("1");

        //Then
        Assertions.assertEquals(1, fetchedTvShows.size());
        Assertions.assertEquals("Name", fetchedTvShows.get(0).getName());
    }

}
