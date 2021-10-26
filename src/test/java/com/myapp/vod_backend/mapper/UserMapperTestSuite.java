package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.Gender;
import com.myapp.vod_backend.domain.User;
import com.myapp.vod_backend.domain.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTestSuite {

    @Autowired
    private UserMapper mapper;

    @Test
    public void shouldMapToUser() {
        //Given
        UserDto userDto = new UserDto(1, "Name", "Lastname","Birth Date", Gender.FEMALE);

        //When
        User result = mapper.mapToUser(userDto);

        //Then
        assertEquals("Name", result.getName());
        assertEquals(1, result.getId());
    }

    @Test
    public void shouldMapToUserDto() {
        //Given
        User user = new User(3, "Name", "Lastname","Birth Date", Gender.FEMALE);

        //When
        UserDto result = mapper.mapToUserDto(user);

        //Then
        assertEquals("Lastname", result.getLastname());
        assertEquals(3, result.getId());
    }

    @Test
    public void shouldMapToListUserDto() {
        //Given
        List<User> users = List.of(new User(3, "Name", "Lastname","Birth Date", Gender.FEMALE));

        //When
        List<UserDto> result = mapper.mapToListUserDto(users);

        //Then
        assertEquals(1, result.size());
        assertEquals(Gender.FEMALE, result.get(0).getGender());
    }


}
