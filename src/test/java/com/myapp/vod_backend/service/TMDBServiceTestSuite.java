package com.myapp.vod_backend.service;

import com.myapp.vod_backend.tmdb.client.TMDBClient;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TMDBServiceTestSuite {

    @InjectMocks
    private TMDBService service;

    @Mock
    private TMDBClient client;

    @Test
    public void shouldFetchPopularMovies() {
        //Given
        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto("Title", 1, "2010", 200, 10000, 6.7, "Overview"));
        movieDtos.add(new MovieDto("Title2", 2, "2015", 400, 15000, 8.1, "Overview2"));

        when(client.getPopularMovies("1")).thenReturn(movieDtos);

        //When
        List<MovieDto> result = service.fetchPopularMovies("1");

        //Then
        assertEquals(2, result.size());
        assertEquals("Title", result.get(0).getTitle());
    }

    @Test
    public void shouldFetchPopularTvShows() {
        //Given
        List<TvShowDto> tvShowDtos = new ArrayList<>();
        tvShowDtos.add(new TvShowDto(1, "Name", 500, "2010", 5.5, 700, "Overview"));
        tvShowDtos.add(new TvShowDto(2, "Name2", 200, "2015", 6.5, 900, "Overview2"));

        when(client.getPopularTvShows("1")).thenReturn(tvShowDtos);

        //When
        List<TvShowDto> result = service.fetchPopularTvShows("1");

        //Then
        assertEquals(2, result.size());
        assertEquals("Name", result.get(0).getName());
    }

    @Test
    public void shouldFetchMoviesByQuery() {
        //Given
        List<MovieDto> movieDtos = new ArrayList<>();
        movieDtos.add(new MovieDto("Title", 1, "2010", 200, 10000, 6.7, "Overview"));
        movieDtos.add(new MovieDto("Title2", 2, "2015", 400, 15000, 8.1, "Overview2"));

        when(client.searchMoviesByQuery("Title", "1")).thenReturn(movieDtos);

        //When
        List<MovieDto> result = service.fetchMoviesByQuery("Title", "1");

        //Then
        assertEquals(2, result.size());
        assertEquals("Title", result.get(0).getTitle());
    }

    @Test
    public void shouldFetchTvShowsByQuery() {
        //Given
        List<TvShowDto> tvShowDtos = new ArrayList<>();
        tvShowDtos.add(new TvShowDto(1, "Name", 500, "2010", 5.5, 700, "Overview"));
        tvShowDtos.add(new TvShowDto(2, "Name2", 200, "2015", 6.5, 900, "Overview2"));

        when(client.searchTvShowsByQuery("Name", "1")).thenReturn(tvShowDtos);

        //When
        List<TvShowDto> result = service.fetchTvShowsByQuery("Name", "1");

        //Then
        assertEquals(2, result.size());
        assertEquals("Name", result.get(0).getName());
    }
}
