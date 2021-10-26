package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.exception.AccountNotFoundException;
import com.myapp.vod_backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTestSuite {

    @Autowired
    private AccountService accountService;

    @Autowired
    private WatchListRepository watchListRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TvShowRepository tvShowRepository;

    @Autowired
    private RentMovieRepository rentMovieRepository;

    @Autowired
    private RentTvShowRepository rentTvShowRepository;

    private User user;
    private Account account;
    private User user2;
    private Account account2;
    private List<Movie> movies = new ArrayList<>();
    private List<TvShow> tvShows = new ArrayList<>();

    @BeforeEach
    public void beforeEachTest() {
        user = new User("Name", "Lastname", "Date", Gender.MALE);
        account = new Account("Password", "Email", "Poland", "Polish", Role.USER, user);

        user2 = new User("Name2", "Lastname2", "Date2", Gender.FEMALE);
        account2 = new Account("Password2", "Email2", "USA", "US", Role.ADMIN, user2);

        Movie movie = new Movie("Title", 100, "2020", 130.00, 300, 7.7, "Overview");
        Movie savedMovie = movieRepository.save(movie);

        TvShow tvShow = new TvShow(200, "Name", 150.50, "2021", 8.0, 300, "Overview");
        TvShow savedTvShow = tvShowRepository.save(tvShow);

        movies.add(savedMovie);
        tvShows.add(savedTvShow);
    }

    @Test
    public void shouldCreateAccount() {
        //Given

        //When
        Account result = accountService.create(account);

        //Then
        assertEquals("Name", result.getUser().getName());
        assertEquals("Password", result.getPassword());
        assertNotEquals(0, result.getId());
    }

    @Test
    public void shouldSaveAccount() {
        //Given


        //When
        Account result = accountService.save(account);

        //Then
        assertNotEquals(0, result.getId());
        assertEquals(Gender.MALE, result.getUser().getGender());
    }

    @Test
    public void shouldGetAccount() throws AccountNotFoundException {
        //Given
        Account savedAccount = accountService.create(account);

        //When
        Account result = accountService.getAccount(savedAccount.getId()).orElseThrow(() -> new AccountNotFoundException("Account not found"));

        //Then
        assertEquals("Email", result.getEmail());
        assertNotEquals(0, result.getId());
        assertEquals(Role.USER, result.getRole());
    }

    @Test
    public void shouldGetAccounts() {
        //Given
        accountService.save(account);
        accountService.create(account2);

        //When
        List<Account> accounts = accountService.getAccounts();

        //Then
        assertEquals(2, accounts.size());
        assertEquals("USA", accounts.get(1).getCountry());
        assertEquals("Name2", accounts.get(1).getUser().getName());
    }

    @Test
    public void shouldGetAccountByEmailAndPassword() throws AccountNotFoundException {
        //Given
        accountService.save(account);
        accountService.create(account2);

        //When
        Account result = accountService.getAccountByEmailAndPassword("Email", "Password")
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        //Then
        assertEquals("Lastname", result.getUser().getLastname());
        assertEquals("Password", result.getPassword());
    }

    @Test
    public void shouldNotAuthenticate() {
        //Given
        accountService.save(account);
        accountService.create(account2);

        //When
        boolean result = accountService.authenticate("Email2", "Password");

        //Then
        assert !result;
    }

    @Test
    public void shouldAuthenticate() {
        //Given
        accountService.save(account);
        accountService.create(account2);

        //When
        boolean result = accountService.authenticate("Email", "Password");

        //Then
        assertTrue(result);
    }

    @Test
    public void shouldGetWatchlist() throws AccountNotFoundException {
        //Given
        WatchList watchList = new WatchList(null, movies, tvShows);
        account.setWatchList(watchListRepository.save(watchList));
        Account saved = accountService.save(account);

        //When
        WatchList result = accountService.getWatchListForAccount(saved.getId());

        //Then
        assertNotEquals(0, result.getId());
        assertEquals(1, result.getMovies().size());
        assertEquals(1, result.getTvShows().size());
    }

    @Test
    public void shouldDeleteAccount() {
        //Given
        Account saved =accountService.save(account);
        Account saved2 = accountService.create(account2);

        //When
        accountService.deleteAccount(saved.getId());

        //Then
        assertEquals(1, accountService.getAccounts().size());
        assertEquals("Password2", accountService.getAccounts().get(0).getPassword());
    }

    @Test
    public void whenAccountDeletedThenUserBuyAndWatchlistShouldBeDeleted() {
        //Given
        Account saved = accountService.create(account2);

        //When
        accountService.deleteAccount(saved.getId());

        //Then
        assertEquals(0, userRepository.findAll().size());
        assertEquals(0, buyRepository.findAll().size());
        assertEquals(0, watchListRepository.findAll().size());
        assertEquals(0, accountService.getAccounts().size());
    }

    @Test
    public void shouldGetAllRentedMovies() throws AccountNotFoundException {
        //Given
        Account saved = accountService.create(account2);

        RentMovie rentMovie = new RentMovie(null, saved, "now", "tomorrow", movies.get(0));
        RentMovie savedRent = rentMovieRepository.save(rentMovie);

        saved.getRentMovies().add(savedRent);

        Account saved2 = accountService.save(saved);

        //When
        List<Movie> rentedMovies = accountService.getAllRentedMoviesForAccount(saved2.getId());

        //Then
        assertEquals(7.7, rentedMovies.get(0).getVoteAverage());
        assertEquals(1, rentedMovies.size());
    }

    @Test
    public void shouldGetAllRentedTvShows() throws AccountNotFoundException {
        //Given
        Account saved = accountService.create(account2);

        RentTvShow rentTvShow = new RentTvShow(null, saved, "now", "tomorrow", tvShows.get(0));
        RentTvShow savedRent = rentTvShowRepository.save(rentTvShow);

        saved.getRentTvShows().add(savedRent);

        Account saved2 = accountService.save(saved);

        //When
        List<TvShow> rentedTvShows = accountService.getAllRentedTvShowsForAccount(saved2.getId());

        //Then
        assertEquals(8.0, rentedTvShows.get(0).getVoteAverage());
        assertEquals(1, rentedTvShows.size());
    }

    @Test
    public void shouldGetAllMovieRents() throws AccountNotFoundException {
        //Given
        Account saved = accountService.create(account2);

        RentMovie rentMovie = new RentMovie(null, saved, "now", "tomorrow", movies.get(0));
        RentMovie savedRent = rentMovieRepository.save(rentMovie);

        saved.getRentMovies().add(savedRent);

        Account saved2 = accountService.save(saved);

        //When
        List<RentMovie> movieRents = accountService.getMovieRentsForAccount(saved2.getId());

        //Then
       assertEquals(1, movieRents.size());
       assertEquals("2020", movieRents.get(0).getMovie().getReleaseDate());
    }

    @Test
    public void shouldGetAllTvShowRents() throws AccountNotFoundException {
        //Given
        Account saved = accountService.create(account2);

        RentTvShow rentTvShow = new RentTvShow(null, saved, "now", "tomorrow", tvShows.get(0));
        RentTvShow savedRent = rentTvShowRepository.save(rentTvShow);

        saved.getRentTvShows().add(savedRent);

        Account saved2 = accountService.save(saved);

        //When
        List<RentTvShow> tvShowRents = accountService.getTvShowRentsForAccount(saved2.getId());

        //Then
        assertEquals(1, tvShowRents.size());
        assertEquals(200, tvShowRents.get(0).getTvShow().getId());
    }
}
