package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.mapper.MovieMapper;
import com.myapp.vod_backend.mapper.TvShowMapper;
import com.myapp.vod_backend.service.MovieService;
import com.myapp.vod_backend.service.TvShowService;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TvShowController.class)
public class TvShowControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TvShowMapper mapper;

    @MockBean
    private TvShowService service;

    @Test
    public void shouldGetTvShow() throws Exception {
        //Given
        TvShow tvShow = new TvShow(3, "Arrow", 300.0, "2013", 7.9, 15000, "Overview Arrow");
        TvShowDto tvShowDto = new TvShowDto(7, "Arrow DTO", 300.0, "2013 DTO", 7.9, 15000, "Overview Arrow DTO");

        when(service.getTvShowById(any())).thenReturn(Optional.of(tvShow));
        when(mapper.mapToTvShowDto(tvShow)).thenReturn(tvShowDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/tvShows/{tvShowId}", 7)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Arrow DTO"));
    }

    @Test
    public void shouldGetTvShows() throws Exception {
        //Given
        List<TvShow> tvShows = List.of(new TvShow(3, "Arrow", 300.0, "2013", 7.9,
                15000, "Overview Arrow"));
        List<TvShowDto> tvShowDtoList = List.of(new TvShowDto(7, "Arrow DTO", 300.0,
                "2013 DTO", 7.9, 15000, "Overview Arrow DTO"));

        when(service.getTvShows()).thenReturn(tvShows);
        when(mapper.mapToListTvShowDto(tvShows)).thenReturn(tvShowDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/tvShows")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Arrow DTO"));
    }

    @Test
    public void shouldGetTvShowsWithAverageAbove() throws Exception {
        //Given
        List<TvShow> tvShows = List.of(new TvShow(3, "Arrow", 300.0, "2013", 7.9,
                15000, "Overview Arrow"));
        List<TvShowDto> tvShowDtoList = List.of(new TvShowDto(7, "Arrow DTO", 300.0,
                "2013 DTO", 7.9, 15000, "Overview Arrow DTO"));

        when(service.getTvShowsWithAverageAbove(7.0)).thenReturn(tvShows);
        when(mapper.mapToListTvShowDto(tvShows)).thenReturn(tvShowDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/tvShows/searchByRatings")
        .param("average", "7.0")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Arrow DTO"));
    }

    @Test
    public void shouldGetTvShowsByKeyword() throws Exception {
        //Given
        List<TvShow> tvShows = List.of(new TvShow(3, "Arrow", 300.0, "2013", 7.9,
                15000, "Overview Arrow"));
        List<TvShowDto> tvShowDtoList = List.of(new TvShowDto(7, "Arrow DTO", 300.0,
                "2013 DTO", 7.9, 15000, "Overview Arrow DTO"));

        when(service.getTvShowsByKeyword("Arrow")).thenReturn(tvShows);
        when(mapper.mapToListTvShowDto(tvShows)).thenReturn(tvShowDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/tvShows/searchByKeyword")
        .param("keyword", "Arrow")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Arrow DTO"));
    }
}
