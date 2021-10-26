package com.myapp.vod_backend.controller;

import com.google.gson.Gson;
import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.domain.dto.RentTvShowDto;
import com.myapp.vod_backend.mapper.RentTvShowMapper;
import com.myapp.vod_backend.service.RentTvShowService;
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

@WebMvcTest(RentTvShowController.class)
public class RentTvShowControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentTvShowMapper mapper;

    @MockBean
    private RentTvShowService service;

    private TvShow tvShow;
    private Account account;

    @BeforeEach
    public void beforeEachTest() {
        account = new Account(666, "Password", "Email", "Country", "Language", Role.USER, false, new User());
        tvShow = new TvShow(101, "Daredevil", 150000.0, "2015", 8.5, 90000, "Overview");
    }

    @Test
    public void shouldSaveRentTvShow() throws Exception {
        //Given
        RentTvShow rentTvShow = new RentTvShow(7, account, "Rent Date", "Expire Date", tvShow);
        RentTvShowDto rentTvShowDto = new RentTvShowDto(8, account.getId(), "Rent Date DTO", "Expire Date DTO", tvShow.getId());

        when(service.saveRentTvShow(rentTvShow)).thenReturn(rentTvShow);
        when(mapper.mapToRentTvShow(rentTvShowDto)).thenReturn(rentTvShow);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(rentTvShowDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/rentTvShows")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRentTvShow() throws Exception {
        //Given
        RentTvShow rentTvShow = new RentTvShow(7, account, "Rent Date", "Expire Date", tvShow);
        RentTvShowDto rentTvShowDto = new RentTvShowDto(8, account.getId(), "Rent Date DTO", "Expire Date DTO", tvShow.getId());

        when(service.rentTvShow(rentTvShow)).thenReturn(rentTvShow);
        when(mapper.mapToRentTvShow(any(RentTvShowDto.class))).thenReturn(rentTvShow);
        when(mapper.mapToRentTvShowDto(rentTvShow)).thenReturn(rentTvShowDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(rentTvShowDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/rentTvShows")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.accountId").value(666));
    }

    @Test
    public void shouldGetRentTvShow() throws Exception {
        //Given
        RentTvShow rentTvShow = new RentTvShow(7, account, "Rent Date", "Expire Date", tvShow);
        RentTvShowDto rentTvShowDto = new RentTvShowDto(8, account.getId(), "Rent Date DTO", "Expire Date DTO", tvShow.getId());

        when(service.getRentForTvShow(any())).thenReturn(Optional.of(rentTvShow));
        when(mapper.mapToRentTvShowDto(rentTvShow)).thenReturn(rentTvShowDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/rentTvShows/{rentTvShowId}", 20)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.tvShowId").value(101));
    }

    @Test
    public void shouldDeleteRentTvShow() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/v1/rentTvShows/{rentTvShowId}", 20)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetAllRentTvShows() throws Exception {
        //Given
        List<RentTvShow> rentTvShows = List.of(new RentTvShow(7, account, "Rent Date", "Expire Date", tvShow));
        List<RentTvShowDto> rentTvShowDtoList = List.of(new RentTvShowDto(8, account.getId(), "Rent Date DTO", "Expire Date DTO", tvShow.getId()));

        when(service.getAllRentsForTvShows()).thenReturn(rentTvShows);
        when(mapper.mapToListRentTvShowDto(rentTvShows)).thenReturn(rentTvShowDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/rentTvShows")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(8))
                .andExpect(jsonPath("$[0].expireDate").value("Expire Date DTO"));
    }
}
