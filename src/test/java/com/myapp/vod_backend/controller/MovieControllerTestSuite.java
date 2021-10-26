package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.mapper.MovieMapper;
import com.myapp.vod_backend.service.MovieService;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieMapper mapper;

    @MockBean
    private MovieService service;

    @Test
    public void shouldGetMovie() throws Exception {
        //Given
        Movie movie = new Movie("Captain America: Civil War", 5, "2018", 150000.0,
                500000, 8.0, "Overview Cap");
        MovieDto movieDto = new MovieDto("Captain America: Civil War DTO", 5, "2018 DTO",
                150000.0, 500000, 8.0, "Overview Cap DTO");

        when(service.getMovieById(any())).thenReturn(Optional.of(movie));
        when(mapper.mapToMovieDto(movie)).thenReturn(movieDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/movies/{movieId}", 5)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.title").value("Captain America: Civil War DTO"));
    }

    @Test
    public void shouldGetMovies() throws Exception {
        //Given
        List<Movie> movies = List.of(new Movie("Captain America: Civil War", 5, "2018",
                150000.0, 500000, 8.0, "Overview Cap"));
        List<MovieDto> movieDtoList = List.of(new MovieDto("Captain America: Civil War DTO", 5,
                "2018 DTO", 150000.0, 500000, 8.0, "Overview Cap DTO"));

        when(service.getMovies()).thenReturn(movies);
        when(mapper.mapToListMovieDto(movies)).thenReturn(movieDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/movies")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Captain America: Civil War DTO"));
    }

    @Test
    public void shouldGetMoviesWithAverageAbove() throws Exception {
        //Given
        List<Movie> movies = List.of(new Movie("Captain America: Civil War", 5, "2018",
                150000.0, 500000, 8.0, "Overview Cap"));
        List<MovieDto> movieDtoList = List.of(new MovieDto("Captain America: Civil War DTO", 5,
                "2018 DTO", 150000.0, 500000, 8.0, "Overview Cap DTO"));

        when(service.getMoviesWithAverageAbove(7.0)).thenReturn(movies);
        when(mapper.mapToListMovieDto(movies)).thenReturn(movieDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/movies/searchByRatings")
        .param("average", "7.0")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Captain America: Civil War DTO"));
    }

    @Test
    public void shouldGetMoviesByKeyword() throws Exception {
        //Given
        List<Movie> movies = List.of(new Movie("Captain America: Civil War", 5, "2018",
                150000.0, 500000, 8.0, "Overview Cap"));
        List<MovieDto> movieDtoList = List.of(new MovieDto("Captain America: Civil War DTO", 5,
                "2018 DTO", 150000.0, 500000, 8.0, "Overview Cap DTO"));

        when(service.getMoviesByKeyword("Captain")).thenReturn(movies);
        when(mapper.mapToListMovieDto(movies)).thenReturn(movieDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/movies/searchByKeyword")
        .param("keyword", "Captain")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Captain America: Civil War DTO"));
    }
}
