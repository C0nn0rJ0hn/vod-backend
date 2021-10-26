package com.myapp.vod_backend.controller;

import com.google.gson.Gson;
import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.domain.dto.RentMovieDto;
import com.myapp.vod_backend.mapper.RentMovieMapper;
import com.myapp.vod_backend.service.RentMovieService;
import org.junit.jupiter.api.BeforeEach;
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

@WebMvcTest(RentMovieController.class)
public class RentMovieControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentMovieMapper mapper;

    @MockBean
    private RentMovieService service;

    private Movie movie;
    private Account account;

    @BeforeEach
    public void beforeEachTest() {
        movie = new Movie("The Dark Knight", 101, "2008", 10000.0, 15000, 8.1, "Overview");
        account = new Account(999, "Password", "Email", "Country", "Language", Role.USER, false, new User());
    }

    @Test
    public void shouldSaveRentMovie() throws Exception {
        //Given
        RentMovie rentMovie = new RentMovie(5, account, "Rent Date", "Expire Date", movie);
        RentMovieDto rentMovieDto = new RentMovieDto(10, account.getId(), "Rent Date DTO", "Expire Date DTO", movie.getId());


        when(service.saveRentMovie(rentMovie)).thenReturn(rentMovie);
        when(mapper.mapToRentMovie(rentMovieDto)).thenReturn(rentMovie);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(rentMovieDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/rentMovies")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRentMovie() throws Exception {
        //Given
        RentMovie rentMovie = new RentMovie(5, account, "Rent Date", "Expire Date", movie);
        RentMovieDto rentMovieDto = new RentMovieDto(10, account.getId(), "Rent Date DTO", "Expire Date DTO", movie.getId());

       when(service.rentMovie(rentMovie)).thenReturn(rentMovie);
       when(mapper.mapToRentMovie(any(RentMovieDto.class))).thenReturn(rentMovie);
       when(mapper.mapToRentMovieDto(rentMovie)).thenReturn(rentMovieDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(rentMovieDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/rentMovies")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.movieId").value(101));
    }

    @Test
    public void shouldDeleteRentMovie() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/v1/rentMovies/{rentMovieId}", 10)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetRentMovie() throws Exception {
        //Given
        RentMovie rentMovie = new RentMovie(5, account, "Rent Date", "Expire Date", movie);
        RentMovieDto rentMovieDto = new RentMovieDto(10, account.getId(), "Rent Date DTO", "Expire Date DTO", movie.getId());

       when(service.getRentedMovie(any())).thenReturn(Optional.of(rentMovie));
       when(mapper.mapToRentMovieDto(rentMovie)).thenReturn(rentMovieDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/rentMovies/{rentMovieId}", 10)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.movieId").value(101))
                .andExpect(jsonPath("$.rentDate").value("Rent Date DTO"));
    }

    @Test
    public void shouldGetRentMovies() throws Exception {
        //Given
        List<RentMovie> rentMovies = List.of(new RentMovie(5, account, "Rent Date", "Expire Date", movie));
        List<RentMovieDto> rentMovieDtoList = List.of(new RentMovieDto(10, account.getId(), "Rent Date DTO", "Expire Date DTO", movie.getId()));

        when(service.getRentedMovies()).thenReturn(rentMovies);
        when(mapper.mapToListRentMovieDto(rentMovies)).thenReturn(rentMovieDtoList);


        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/rentMovies")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].accountId").value(999))
                .andExpect(jsonPath("$[0].expireDate").value("Expire Date DTO"));
    }
}
