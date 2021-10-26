package com.myapp.vod_backend.controller;

import com.google.gson.Gson;
import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.domain.WatchList;
import com.myapp.vod_backend.domain.dto.WatchListDto;
import com.myapp.vod_backend.mapper.*;
import com.myapp.vod_backend.service.WatchListService;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WatchListController.class)
public class WatchListControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WatchListService watchListService;

    @MockBean
    private WatchListMapper watchListMapper;

    @MockBean
    private MovieMapper movieMapper;

    @MockBean
    private TvShowMapper tvShowMapper;


    private List<Movie> movies = new ArrayList<>();
    private List<TvShow> tvShows = new ArrayList<>();

    @BeforeEach
    public void beforeEachTest() {
        Movie movie = new Movie("Black Adam", 20, "Release2", 400.0, 300, 7.1, "Overview2");
        Movie movie1 = new Movie("Black Panther", 30, "Release3", 300.0, 400, 7.9, "Overview3");
        movies.add(movie);
        movies.add(movie1);
        TvShow tvShow = new TvShow(40, "Daredevil", 500.0, "2016", 8.2, 20000, "Overview");
        TvShow tvShow1 = new TvShow(50, "Banshee", 300.0, "2013", 7.9, 15000, "Overview2");
        tvShows.add(tvShow);
        tvShows.add(tvShow1);
    }

    @Test
    public void shouldSaveWatchlist() throws Exception {
        //Given
        WatchList watchList = new WatchList(1, movies, tvShows);
        WatchListDto watchListDto = new WatchListDto(5, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList()));

        when(watchListService.save(any())).thenReturn(watchList);
        when(watchListMapper.mapToWatchList(watchListDto)).thenReturn(watchList);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(watchListDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/watchlists")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetWatchlists() throws Exception {
        //Given
        List<WatchList> watchLists = List.of(new WatchList(1, movies, tvShows));
        List<WatchListDto> watchListDtoList = List.of(new WatchListDto(5, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList())));

        when(watchListService.getWatchLists()).thenReturn(watchLists);
        when(watchListMapper.mapToListWatchListDto(watchLists)).thenReturn(watchListDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/watchlists")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(5))
                .andExpect(jsonPath("$[0].moviesId[0]").value(20));
    }

    @Test
    public void shouldGetWatchlist() throws Exception {
        //Given
        WatchList watchList = new WatchList(1, movies, tvShows);
        WatchListDto watchListDto = new WatchListDto(5, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList()));

        when(watchListService.getWatchList(any())).thenReturn(Optional.of(watchList));
        when(watchListMapper.mapToWatchListDto(watchList)).thenReturn(watchListDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/watchlists/{watchlistId}", 5)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.moviesId[0]").value(20))
                .andExpect(jsonPath("$.tvShowsId[0]").value(40));
    }

    @Test
    public void shouldDeleteWatchlist() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/v1/watchlists/{watchlistId}", 5)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetMoviesInWatchlist() throws Exception {
        //Given
        List<MovieDto> movieDtoList = List.of(new MovieDto("Black Adam DTO", 20, "Release2 DTO", 400.0,
                300, 7.1, "Overview2 DTO"), new MovieDto("Black Panther DTO", 30, "Release3 DTO",
                300.0, 400, 7.9, "Overview3 DTO"));

        when(watchListService.getAllMoviesInWatchlist(any())).thenReturn(movies);
        when(movieMapper.mapToListMovieDto(movies)).thenReturn(movieDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/watchlists/{watchlistId}/movies", 5)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Black Adam DTO"))
                .andExpect(jsonPath("$[1].vote_average").value(7.9));
    }

    @Test
    public void shouldGetTvShowsInWatchlist() throws Exception {
        //Given
        List<TvShowDto> tvShowDtoList = List.of(new TvShowDto(40, "Daredevil DTO", 500.0, "2016 DTO",
                        8.2, 20000, "Overview DTO"), new TvShowDto(50, "Banshee DTO",
                300.0, "2013 DTO", 7.9, 15000, "Overview2 DTO"));

        when(watchListService.getAllTvShowsInWatchList(any())).thenReturn(tvShows);
        when(tvShowMapper.mapToListTvShowDto(tvShows)).thenReturn(tvShowDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/watchlists/{watchlistId}/tvShows", 5)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Daredevil DTO"))
                .andExpect(jsonPath("$[1].first_air_date").value("2013 DTO"));
    }

    @Test
    public void shouldAdTvShowToWatchlist() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/watchlists/{watchlistId}/addTvShow", 5)
        .param("tvShowId", "30")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAdMovieToWatchlist() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/watchlists/{watchlistId}/addMovie", 5)
        .param("movieId", "20")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRemoveTvShowFromWatchlist() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/v1/watchlists/{watchlistId}/removeTvShow", 5)
        .param("tvShowId", "30")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteMovieFromWatchlist() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/v1/watchlists/{watchlistId}/removeMovie", 5)
        .param("movieId", "20")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
