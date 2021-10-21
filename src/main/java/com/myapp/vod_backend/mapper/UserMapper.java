package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.User;
import com.myapp.vod_backend.domain.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getLastname(),
                userDto.getBirthDate(),
                userDto.getGender()
        );
    }

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getBirthDate(),
                user.getGender()
        );
    }

    public List<UserDto> mapToListUserDto(List<User> users) {
        return users.stream().map(this::mapToUserDto).collect(Collectors.toList());
    }
}
