package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.exception.RentMovieNotFoundException;
import com.myapp.vod_backend.repository.AccountRepository;
import com.myapp.vod_backend.repository.UserRepository;
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
public class RentMovieServiceTestSuite {

    @Autowired
    private RentMovieService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private RentMovie rentMovie2;
    private RentMovie rentMovie;


    @BeforeEach
    public void beforeEachTest() {
        User user = new User("John", "Connor", "20.03.1990", Gender.MALE);
        User savedUser = userRepository.save(user);
        Account account = new Account("Pass", "mail@mail.com", "PL", "pl", Role.USER, savedUser);
        Account savedAccount = accountRepository.save(account);
        Movie movie = new Movie("Title1", 10, "Release1", 500.0, 200, 6.6, "Overview1");
        rentMovie = new RentMovie(savedAccount, movie);
        service.saveRentMovie(rentMovie);
        Movie movie2 = new Movie("Black Adam", 20, "Release2", 400.0, 300, 7.1, "Overview3");
        rentMovie2 = new RentMovie(savedAccount, movie2);
    }

    @Test
    public void shouldGetAllRentedMovies() {
        //Given
        RentMovie saved = service.saveRentMovie(rentMovie2);

        //When
        List<RentMovie> result = service.getRentedMovies();

        //Then
        assertEquals(2, result.size());
    }

    @Test
    public void shouldSaveRentMovie() {
        //Given

        //When
        RentMovie result = service.saveRentMovie(rentMovie2);

        //Then
        assertEquals(2, service.getRentedMovies().size());
        assertEquals("Black Adam", result.getMovie().getTitle());
    }

    @Test
    public void shouldGetRentMovie() {
        //Given
        RentMovie saved = service.saveRentMovie(rentMovie2);

        //When
        RentMovie result = service.getRentedMovie(saved.getId()).orElseThrow(() -> new RentMovieNotFoundException("Rent movie not found"));

        //Then
        assertNotEquals(0, result.getId());
        assertEquals("mail@mail.com", result.getAccount().getEmail());
    }

    @Test
    public void shouldDeleteRentMovie() {
        //Given
        RentMovie saved = service.saveRentMovie(rentMovie2);

        //When
        service.deleteRentedMovie(saved.getId());

        //Then
        assertEquals(1, service.getRentedMovies().size());
        assertEquals("Title1", service.getRentedMovies().get(0).getMovie().getTitle());
    }

    public void shouldCreateRentMovie() {
        //Given

        //When
        RentMovie result = service.rentMovie(rentMovie2);

        //Then
        assertEquals(2, service.getRentedMovies().size());
        assertNotEquals(null, result.getRentDate());
    }
}
