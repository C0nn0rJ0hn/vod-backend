package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.domain.dto.UserDto;
import com.myapp.vod_backend.exception.UserNotFoundException;
import com.myapp.vod_backend.mapper.UserMapper;
import com.myapp.vod_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;


    @GetMapping(value = "/users")
    public List<UserDto> getUsers() {
        return mapper.mapToListUserDto(service.getUsers());
    }

    @GetMapping(value = "/users/{userId}")
    public UserDto getUser(@PathVariable Integer userId) throws UserNotFoundException {
        return mapper.mapToUserDto(service.getUser(userId).orElseThrow(() -> new UserNotFoundException("User not found in database")));
    }

    @DeleteMapping(value = "users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        service.deleteUser(userId);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto saveUser(@RequestBody UserDto userDto) {
        return mapper.mapToUserDto(service.save(mapper.mapToUser(userDto)));
    }

    @PutMapping(value = "/users")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return mapper.mapToUserDto(service.save(mapper.mapToUser(userDto)));
    }

    @GetMapping(value = "/users/byName")
    public List<UserDto> getUsersByName(@RequestParam String userName) {
        return mapper.mapToListUserDto(service.getUsersByName(userName));
    }

    @GetMapping(value = "/users/byLastname")
    public List<UserDto> getUsersByLastname(@RequestParam String userLastname) {
        return mapper.mapToListUserDto(service.getUsersByLastname(userLastname));
    }

    @GetMapping(value = "/users/byNameAndLastname")
    public List<UserDto> getUsersByNameAndLastname(@RequestParam String userName, @RequestParam String userLastname) {
        return mapper.mapToListUserDto(service.getUsersByNameAndLastname(userName, userLastname));
    }

    @GetMapping(value = "/users/account/{accountId}")
    public UserDto getUserByAccountId(@PathVariable Integer accountId) throws UserNotFoundException{
        return mapper.mapToUserDto(service.getUserByAccountId(accountId).orElseThrow(() -> new UserNotFoundException("Invalid accountId. User not found")));
    }
}
