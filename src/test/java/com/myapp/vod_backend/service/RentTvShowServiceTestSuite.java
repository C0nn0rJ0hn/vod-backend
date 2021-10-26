package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.exception.RentTvShowNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class RentTvShowServiceTestSuite {

    @Autowired
    private RentTvShowService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private RentTvShow rentTvShow;
    private RentTvShow rentTvShow2;


    @BeforeEach
    public void beforeEachTest() {
        User user = new User("John", "Connor", "20.03.1990", Gender.MALE);
        User savedUser = userRepository.save(user);
        Account account = new Account("Pass", "mail@mail.com", "PL", "pl", Role.USER, savedUser);
        Account savedAccount = accountRepository.save(account);
        TvShow tvShow = new TvShow(1, "Daredevil", 500.0, "2016", 8.2, 20000, "Overview");
        TvShow tvShow2 = new TvShow(2, "Banshee", 300.0, "2013", 7.9, 15000, "Overview2");
        rentTvShow = new RentTvShow(savedAccount, tvShow);
        rentTvShow2 = new RentTvShow(savedAccount, tvShow2);
        service.saveRentTvShow(rentTvShow2);
    }

    @Test
    public void shouldGetAllRentTvShows() {
        //Given
        service.saveRentTvShow(rentTvShow);

        //When
        List<RentTvShow> result = service.getAllRentsForTvShows();

        //Then
        assertEquals(2, result.size());
    }

    @Test
    public void shouldSaveRentTvShow() {
        //Given

        //When
        RentTvShow result = service.saveRentTvShow(rentTvShow);

        //Then
        assertEquals(2, service.getAllRentsForTvShows().size());
        assertEquals("Daredevil", result.getTvShow().getName());
    }

    @Test
    public void shouldGetRentTvShow() {
        //Given
        RentTvShow saved = service.saveRentTvShow(rentTvShow);

        //When
        RentTvShow result = service.getRentForTvShow(saved.getId()).orElseThrow(() -> new RentTvShowNotFoundException("Rent tv show not found"));

        //Then
        assertNotEquals(0, result.getId());
        assertEquals("mail@mail.com", result.getAccount().getEmail());
    }

    @Test
    public void shouldDeleteRentTvShow() {
        //Given
        RentTvShow saved = service.saveRentTvShow(rentTvShow);

        //When
        service.deleteRentTvShow(saved.getId());

        //Then
        assertEquals(1, service.getAllRentsForTvShows().size());
        assertEquals("Banshee", service.getAllRentsForTvShows().get(0).getTvShow().getName());
    }

    public void shouldCreateRentTvShow() {
        //Given

        //When
        RentTvShow result = service.rentTvShow(rentTvShow);

        //Then
        assertEquals(2, service.getAllRentsForTvShows().size());
        assertNotEquals(null, result.getRentDate());
    }
}
