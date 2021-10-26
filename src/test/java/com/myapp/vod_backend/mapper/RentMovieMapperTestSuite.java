package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.domain.dto.RentMovieDto;
import com.myapp.vod_backend.repository.AccountRepository;
import com.myapp.vod_backend.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class RentMovieMapperTestSuite {

    @Autowired
    private RentMovieMapper mapper;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private MovieRepository movieRepository;

    private Account account;
    private Movie movie;

    @BeforeEach
    public void beforeEachTest() {
        WatchList watchList = new WatchList(10, new ArrayList<>(), new ArrayList<>());
        User user = new User(12, "Name", "Lastname", "Birth Date", Gender.MALE);
        Buy buy = new Buy(13, new ArrayList<>(), new ArrayList<>());

        account = new Account(1, "Created At", "Updated at", "Password",
                "Email", "Country", "Language", Role.USER, false, user, watchList,
                buy, new ArrayList<>(), new ArrayList<>());
        movie = new Movie("Black Panther", 4, "2019", 300.0, 400, 7.9, "Overview Black Panther");
    }

    @Test
    public void shouldMapToRentMovie() {
        //Given
        RentMovieDto rentMovieDto = new RentMovieDto(20 ,account.getId(), "Rent Date", "Expire Date", movie.getId());

        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));

        //When
        RentMovie result = mapper.mapToRentMovie(rentMovieDto);

        //Then
        assertEquals(20, result.getId());
        assertEquals(10, result.getAccount().getWatchList().getId());
        assertEquals("Name", result.getAccount().getUser().getName());
        assertEquals("Black Panther", result.getMovie().getTitle());
    }

    @Test
    public void shouldMapToRentMovieDto() {
        //Given
        RentMovie rentMovie = new RentMovie(20 ,account, "Rent Date", "Expire Date", movie);

        //When
        RentMovieDto result = mapper.mapToRentMovieDto(rentMovie);

        //Then
        assertEquals(20, result.getId());
        assertEquals(4, result.getMovieId());
        assertEquals(1, result.getAccountId());
    }

    @Test
    public void shouldMapToListRentMovieDto() {
        //Given
        List<RentMovie> rentMovies = List.of(new RentMovie(20 ,account, "Rent Date", "Expire Date", movie));

        //When
        List<RentMovieDto> result = mapper.mapToListRentMovieDto(rentMovies);

        //Then
        assertEquals(1, result.size());
        assertEquals(20, result.get(0).getId());
        assertEquals("Rent Date", result.get(0).getRentDate());
    }
}
