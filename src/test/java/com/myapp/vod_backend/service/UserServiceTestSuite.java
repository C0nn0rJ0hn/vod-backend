package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.exception.UserNotFoundException;
import com.myapp.vod_backend.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class UserServiceTestSuite {

    @Autowired
    private UserService service;

    @Autowired
    private AccountRepository accountRepository;

    private User user;
    private User user2;
    private User user3;

    @BeforeEach
    public void beforeEachTest() {
        user = new User("John", "Connor", "Date", Gender.MALE);
        service.save(user);
        Account account = new Account("Password", "Email", "Poland", "Polish", Role.USER, user);
        accountRepository.save(account);
        user2 = new User("James", "Kowalski", "Date2", Gender.FEMALE);
        Account account2 = new Account("Password2", "Email2", "USA", "US", Role.ADMIN, user2);
        service.save(user2);
        accountRepository.save(account2);
        user3 = new User("James", "Buszyński", "Date3", Gender.FEMALE);
    }

    @Test
    public void shouldGetAllUsers() {
        //Given
        service.save(user3);

        //When
        List<User> result = service.getUsers();

        //Then
        assertEquals(3, result.size());
    }

    @Test
    public void shouldSaveUser() {
        //Given

        //When
        User result = service.save(user3);

        //Then
        assertEquals("James", result.getName());
        assertEquals(3, service.getUsers().size());
    }

    @Test
    public void shouldGetUserById() throws UserNotFoundException {
        //Given
        User saved = service.save(user3);

        //When
        User result = service.getUser(saved.getId()).orElseThrow(() -> new UserNotFoundException("USer not found"));

        //Then
        assertNotEquals(0, result.getId());
        assertEquals("Buszyński", result.getLastname());
    }

    @Test
    public void shouldDeleteUserById() {
        //Given
        User saved = service.save(user3);

        //When
        service.deleteUser(saved.getId());

        //Then
        assertEquals(2, service.getUsers().size());
    }

    @Test
    public void shouldGetUsersByName() {
        //Given
        service.save(user3);

        //When
        List<User> result = service.getUsersByName("James");

        //Then
        assertEquals(2, result.size());
    }

    @Test
    public void shouldGetUsersByLastname() {
        //Given
        service.save(user3);

        //When
        List<User> result = service.getUsersByLastname("Kowalski");

        //Then
        assertEquals(1, result.size());
    }

    @Test
    public void shouldGetUsersByNameAndLastname() {
        //Given
        service.save(user3);

        //When
        List<User> result = service.getUsersByNameAndLastname("John", "Connor");

        //Then
        assertEquals(1, result.size());
    }

    @Test
    public void shouldGetUserByAccountId() throws UserNotFoundException {
        //Given
        service.save(user3);
        Account account3 = new Account("Password3", "Email3", "USA", "US", Role.ADMIN, user3);
        accountRepository.save(account3);


        //When
        User result = service.getUserByAccountId(account3.getId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        //Then
        assertEquals("Buszyński", result.getLastname());
    }
}
