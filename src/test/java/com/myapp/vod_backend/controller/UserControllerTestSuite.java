package com.myapp.vod_backend.controller;

import com.google.gson.Gson;
import com.myapp.vod_backend.domain.Gender;
import com.myapp.vod_backend.domain.User;
import com.myapp.vod_backend.domain.dto.UserDto;
import com.myapp.vod_backend.mapper.UserMapper;
import com.myapp.vod_backend.service.UserService;
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

@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @MockBean
    private UserMapper mapper;

    @Test
    public void shouldSaveUser() throws Exception {
        //Given
        User user = new User(100, "Name", "Lastname", "Birth Date", Gender.MALE);
        UserDto userDto = new UserDto(200, "Name DTO", "Lastname DTO", "Birth Date DTO", Gender.MALE);

        when(service.save(user)).thenReturn(user);
        when(mapper.mapToUser(any(UserDto.class))).thenReturn(user);
        when(mapper.mapToUserDto(user)).thenReturn(userDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(200))
                .andExpect(jsonPath("$.name").value("Name DTO"));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        //Given
        User user = new User(100, "Name", "Lastname", "Birth Date", Gender.MALE);
        UserDto userDto = new UserDto(400, "Name DTO", "Lastname DTO", "Birth Date DTO", Gender.MALE);

        when(service.save(user)).thenReturn(user);
        when(mapper.mapToUser(any(UserDto.class))).thenReturn(user);
        when(mapper.mapToUserDto(user)).thenReturn(userDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(400))
                .andExpect(jsonPath("$.birthDate").value("Birth Date DTO"));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/v1/users/{userId}", 8)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetUsers() throws Exception {
        //Given
        List<User> users = List.of(new User(100, "Name", "Lastname", "Birth Date", Gender.MALE));
        List<UserDto> userDtoList = List.of(new UserDto(400, "Name DTO", "Lastname DTO", "Birth Date DTO", Gender.MALE));

        when(service.getUsers()).thenReturn(users);
        when(mapper.mapToListUserDto(users)).thenReturn(userDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/users")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].gender").value("MALE"));
    }

    @Test
    public void shouldGetUser() throws Exception {
        //Given
        User user = new User(100, "Name", "Lastname", "Birth Date", Gender.MALE);
        UserDto userDto = new UserDto(400, "Name DTO", "Lastname DTO", "Birth Date DTO", Gender.MALE);

        when(service.getUser(any())).thenReturn(Optional.of(user));
        when(mapper.mapToUserDto(user)).thenReturn(userDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/users/{userId}", 11)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(400))
                .andExpect(jsonPath("$.lastname").value("Lastname DTO"));
    }

    @Test
    public void shouldGetUsersByName() throws Exception {
        //Given
        List<User> users = List.of(new User(100, "Name", "Lastname", "Birth Date", Gender.MALE));
        List<UserDto> userDtoList = List.of(new UserDto(400, "Name DTO", "Lastname DTO", "Birth Date DTO", Gender.MALE));

        when(service.getUsersByName(any())).thenReturn(users);
        when(mapper.mapToListUserDto(users)).thenReturn(userDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/users/byName")
        .param("userName", "Name DTO")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Name DTO"));
    }

    @Test
    public void shouldGetUsersByLastname() throws Exception {
        //Given
        List<User> users = List.of(new User(100, "Name", "Lastname", "Birth Date", Gender.MALE));
        List<UserDto> userDtoList = List.of(new UserDto(400, "Name DTO", "Lastname DTO", "Birth Date DTO", Gender.MALE));

        when(service.getUsersByLastname(any())).thenReturn(users);
        when(mapper.mapToListUserDto(users)).thenReturn(userDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/users/byLastname")
        .param("userLastname", "Lastname DTO")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].lastname").value("Lastname DTO"));
    }

    @Test
    public void shouldGetUsersByNameAndLastname() throws Exception {
        //Given
        List<User> users = List.of(new User(100, "Name", "Lastname", "Birth Date", Gender.MALE));
        List<UserDto> userDtoList = List.of(new UserDto(400, "Name DTO", "Lastname DTO", "Birth Date DTO", Gender.MALE));

        when(service.getUsersByNameAndLastname(any(), anyString())).thenReturn(users);
        when(mapper.mapToListUserDto(users)).thenReturn(userDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/users/byNameAndLastname")
        .param("userName", "Name DTO")
        .param("userLastname", "Lastname DTO")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].lastname").value("Lastname DTO"))
                .andExpect(jsonPath("$[0].name").value("Name DTO"));
    }

    @Test
    public void shouldGetUserByAccountId() throws Exception {
        //Given
        User user = new User(100, "Name", "Lastname", "Birth Date", Gender.MALE);
        UserDto userDto = new UserDto(150, "Name DTO", "Lastname DTO", "Birth Date DTO", Gender.MALE);

        when(service.getUserByAccountId(any())).thenReturn(Optional.of(user));
        when(mapper.mapToUserDto(user)).thenReturn(userDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/users/account/{accountId}", 100)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(150))
                .andExpect(jsonPath("$.birthDate").value("Birth Date DTO"));
    }
}
