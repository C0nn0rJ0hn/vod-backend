package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import com.myapp.vod_backend.tmdb.facade.TMDBFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TMDBController.class)
public class TMDBControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TMDBFacade facade;

    private List<MovieDto> movieDtoList = new ArrayList<>();
    private List<TvShowDto> tvShowDtoList = new ArrayList<>();

    @BeforeEach
    public void beforeEachTest() {
        movieDtoList = List.of(new MovieDto("Black Adam DTO", 20, "Release2 DTO", 400.0,
                300, 7.1, "Overview2 DTO"), new MovieDto("Black Panther DTO", 30, "Release3 DTO",
                300.0, 400, 7.9, "Overview3 DTO"));
        tvShowDtoList = List.of(new TvShowDto(40, "Daredevil DTO", 500.0, "2016 DTO",
                8.2, 20000, "Overview DTO"), new TvShowDto(50, "Banshee DTO",
                300.0, "2013 DTO", 7.9, 15000, "Overview2 DTO"));
    }

    @Test
    public void shouldGetPopularMovies() throws Exception {
        //Given
        when(facade.fetchPopularMovies(any())).thenReturn(movieDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/tmdb/movies/popular/{pageNumber}", 1)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Black Adam DTO"));
    }

    @Test
    public void shouldGetPopularTvShows() throws Exception {
        //Given
        when(facade.fetchPopularTvShows(any())).thenReturn(tvShowDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/tmdb/tv/popular/{pageNumber}", 1)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Daredevil DTO"));
    }

    @Test
    public void shouldGetMoviesByQuery() throws Exception {
        //Given
        when(facade.fetchMoviesByQuery(any(), any())).thenReturn(movieDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/tmdb/movies/search")
        .param("query", "Black")
        .param("page", "1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].title").value("Black Panther DTO"));
    }

    @Test
    public void shouldGetTvShowsByQuery() throws Exception {
        //Given
        when(facade.fetchTvShowsByQuery(any(), any())).thenReturn(tvShowDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/tmdb/tv/search")
        .param("query", "anyString")
        .param("page", "1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].name").value("Banshee DTO"));
    }
}
