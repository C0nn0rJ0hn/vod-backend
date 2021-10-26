package com.myapp.vod_backend.controller;

import com.google.gson.Gson;
import com.myapp.vod_backend.domain.Buy;
import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.domain.dto.BuyDto;
import com.myapp.vod_backend.mapper.BuyMapper;
import com.myapp.vod_backend.mapper.MovieMapper;
import com.myapp.vod_backend.mapper.TvShowMapper;
import com.myapp.vod_backend.service.BuyService;
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

@WebMvcTest(BuyController.class)
public class BuyControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyService service;

    @MockBean
    private BuyMapper mapper;

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
    public void shouldSaveBuy() throws Exception {
        //Given
        Buy buy = new Buy(1, movies, tvShows);
        BuyDto buyDto = new BuyDto(2, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList()));

        when(service.save(any(Buy.class))).thenReturn(buy);
        when(mapper.mapToBuy(buyDto)).thenReturn(buy);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(buyDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/buys")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetBuys() throws Exception {
        //Given
        List<Buy> buys = List.of(new Buy(1, movies, tvShows));
        List<BuyDto> buyDtoList = List.of(new BuyDto(2, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList())));

        when(service.getBuys()).thenReturn(buys);
        when(mapper.mapToListBuyDto(buys)).thenReturn(buyDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/buys")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].moviesId[0]").value(20));
    }

    @Test
    public void shouldGetBuy() throws Exception {
        //Given
        Buy buy = new Buy(1, movies, tvShows);
        BuyDto buyDto = new BuyDto(2, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList()));

        when(service.getBuy(any())).thenReturn(Optional.of(buy));
        when(mapper.mapToBuyDto(buy)).thenReturn(buyDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/buys/{buyId}", 2)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.tvShowsId[0]").value(40));
    }

    @Test
    public void shouldUpdateBuy() throws Exception {
        //Given
        Buy buy = new Buy(1, movies, tvShows);
        BuyDto buyDto = new BuyDto(2, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList()));

        when(service.save(any(Buy.class))).thenReturn(buy);
        when(mapper.mapToBuyDto(buy)).thenReturn(buyDto);
        when(mapper.mapToBuy(any(BuyDto.class))).thenReturn(buy);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(buyDto);


        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/buys")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.tvShowsId[1]").value(50));
    }

    @Test
    public void shouldDeleteBuy() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/v1/buys/{buyId}", 2)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetBoughtMovies() throws Exception {
        //Given
        List<MovieDto> movieDtoList = List.of(new MovieDto("Black Adam DTO", 20, "Release2 DTO", 400.0,
                300, 7.1, "Overview2 DTO"), new MovieDto("Black Panther DTO", 30, "Release3 DTO",
                300.0, 400, 7.9, "Overview3 DTO"));

        when(service.getBoughtMovies(any())).thenReturn(movies);
        when(movieMapper.mapToListMovieDto(movies)).thenReturn(movieDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/buys/{buyId}/movies", 5)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Black Adam DTO"));
    }

    @Test
    public void shouldGetBoughtTvShows() throws Exception {
        //Given
        List<TvShowDto> tvShowDtoList = List.of(new TvShowDto(40, "Daredevil DTO", 500.0, "2016 DTO",
                8.2, 20000, "Overview DTO"), new TvShowDto(50, "Banshee DTO",
                300.0, "2013 DTO", 7.9, 15000, "Overview2 DTO"));

        when(service.getBoughtTvShows(any())).thenReturn(tvShows);
        when(tvShowMapper.mapToListTvShowDto(tvShows)).thenReturn(tvShowDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/buys/{buyId}/tvShows", 7)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].name").value("Banshee DTO"));
    }

    @Test
    public void shouldBuyMovie() throws Exception {
        //Given
        Buy buy = new Buy(1, movies, tvShows);
        BuyDto buyDto = new BuyDto(2, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList()));

        when(service.buyMovie(any(), any())).thenReturn(buy);
        when(mapper.mapToBuyDto(buy)).thenReturn(buyDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/buys/{buyId}/buyMovie", 9)
        .param("movieId", "100")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.moviesId[1]").value("30"));
    }

    @Test
    public void shouldBuyTvShow() throws Exception {
        //Given
        Buy buy = new Buy(1, movies, tvShows);
        BuyDto buyDto = new BuyDto(2, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList()));

        when(service.buyTvShow(any(), any())).thenReturn(buy);
        when(mapper.mapToBuyDto(buy)).thenReturn(buyDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/buys/{buyId}/buyTvShow", 13)
                .param("tvShowId", "300")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.tvShowsId.size()").value(2))
                .andExpect(jsonPath("$.tvShowsId[1]").value("50"));
    }
}
