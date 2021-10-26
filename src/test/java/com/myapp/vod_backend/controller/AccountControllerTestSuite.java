package com.myapp.vod_backend.controller;

import com.google.gson.Gson;
import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.domain.dto.*;
import com.myapp.vod_backend.mapper.*;
import com.myapp.vod_backend.service.AccountService;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountMapper accountMapper;

    @MockBean
    private WatchListMapper watchListMapper;

    @MockBean
    private BuyMapper buyMapper;

    @MockBean
    private MovieMapper movieMapper;

    @MockBean
    private TvShowMapper tvShowMapper;

    @MockBean
    private RentTvShowMapper rentTvShowMapper;

    @MockBean
    private RentMovieMapper rentMovieMapper;

    private List<Movie> movies = new ArrayList<>();
    private List<TvShow> tvShows = new ArrayList<>();

    @BeforeEach
    public void beforeEachTest() {
        Movie movie = new Movie("Black Adam", 20, "Release2", 400.0, 300, 7.1, "Overview2");
        Movie movie1 = new Movie("Black Panther", 30, "Release3", 300.0, 400, 7.9, "Overview3");
        movies.add(movie);
        movies.add(movie1);
        TvShow tvShow = new TvShow(40, "Daredevil", 500.0, "2016", 8.2, 20000, "Overview");
        TvShow tvShow1 = new TvShow(50, "Banshee", 300.0, "2013", 7.9, 15000, "Overview2");
        tvShows.add(tvShow);
        tvShows.add(tvShow1);
    }


    @Test
    public void shouldCreateAccount() throws Exception {
        //Given
        User user = new User(100, "Name", "Lastname", "20-11-1988", Gender.MALE);
        WatchList watchList = new WatchList(200, new ArrayList<>(), new ArrayList<>());
        WatchListDto watchListDto = new WatchListDto(201, new ArrayList<>(), new ArrayList<>());
        Buy buy = new Buy(300, new ArrayList<>(), new ArrayList<>());
        BuyDto buyDto = new BuyDto(301, new ArrayList<>(), new ArrayList<>());
        AccountDto accountDto = new AccountDto(1, "2021", null, "Password Dto", "Email Dto",
                "Country Dto", "Language Dto", Role.USER, false, user.getId(), watchList.getId(), buy.getId(),
                new ArrayList<>(), new ArrayList<>());
        Account account = new Account(2, "2021-12-10", null, "Password", "Email", "Country", "Language", Role.USER, false, user,
                watchList, buy, new ArrayList<>(), new ArrayList<>());
        Movie movie = new Movie("Title1", 10, "Release1", 500.0, 200, 6.6, "Overview1");
        MovieDto movieDto = new MovieDto("Title1Dto", 11, "Release1Dto", 500.0, 200, 6.6, "Overview1Dto");
        RentMovie rentMovie = new RentMovie(400, account, "Rent date", "Expire date", movie);
        RentMovieDto rentMovieDto = new RentMovieDto(401, account.getId(), "Rent Date Dto", "Expire Date Dto", movie.getId());
        TvShow tvShow = new TvShow(20, "Daredevil", 500.0, "2016", 8.2, 20000, "Overview");
        TvShowDto tvShowDto = new TvShowDto(21, "Daredevil Dto", 500.0, "2016 Dto", 8.2, 20000, "Overview Dto");
        RentTvShow rentTvShow = new RentTvShow(500, account, "Rent Date", "Expire Date", tvShow);
        RentTvShowDto rentTvShowDto = new RentTvShowDto(501, account.getId(), "Rent Date Dto", "Expire Date Dto", tvShow.getId());


        when(watchListMapper.mapToWatchList(watchListDto)).thenReturn(watchList);
        when(buyMapper.mapToBuy(buyDto)).thenReturn(buy);
        when(movieMapper.mapToMovie(movieDto)).thenReturn(movie);
        when(rentMovieMapper.mapToRentMovie(rentMovieDto)).thenReturn(rentMovie);
        when(tvShowMapper.mapToTvShow(tvShowDto)).thenReturn(tvShow);
        when(rentTvShowMapper.mapToRentTvShow(rentTvShowDto)).thenReturn(rentTvShow);
        when(accountMapper.mapToAccount(accountDto)).thenReturn(account);
        when(accountService.create(account)).thenReturn(account);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(accountDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .post("/v1/accounts")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetAllAccounts() throws Exception {
        //Given
        List<Account> accounts = List.of(new Account(1, "Created at", "Updated at", "Password", "Email", "Country", "Language",
                Role.USER, false, new User(10, "Name", "Lastname", "Birth", Gender.FEMALE),
                new WatchList(100, new ArrayList<>(), new ArrayList<>()), new Buy(200, new ArrayList<>(), new ArrayList<>()),
                new ArrayList<>(), new ArrayList<>()));
        List<AccountDto> accountsDto = List.of(new AccountDto(2, "Created at Dto", "Updated at Dto", "Password Dto",
                "Email Dto", "Country Dto", "Language Dto",
                Role.USER, false, 10, 100, 200, new ArrayList<>(), new ArrayList<>()));

        when(accountService.getAccounts()).thenReturn(accounts);
        when(accountMapper.mapToListAccountDto(accounts)).thenReturn(accountsDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/accounts")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].userId").value(10));
    }

    @Test
    public void shouldGetAccount() throws Exception {
        //Given
        Account account = new Account(1, "Created at", "Updated at", "Password", "Email", "Country", "Language",
                Role.USER, false, new User(10, "Name", "Lastname", "Birth", Gender.FEMALE),
                new WatchList(100, new ArrayList<>(), new ArrayList<>()), new Buy(200, new ArrayList<>(), new ArrayList<>()),
                new ArrayList<>(), new ArrayList<>());
        AccountDto accountDto = new AccountDto(2, "Created at Dto", "Updated at Dto", "Password Dto",
                "Email Dto", "Country Dto", "Language Dto",
                Role.USER, false, 10, 100, 200, new ArrayList<>(), new ArrayList<>());

        when(accountService.getAccount(1)).thenReturn(Optional.of(account));
        when(accountMapper.mapToAccountDto(account)).thenReturn(accountDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/accounts/{accountId}", 1)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("Email Dto"))
                .andExpect(jsonPath("$.watchlistId").value(100));
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        //Given, When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/v1/accounts/{accountId}", 2)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateAccount() throws Exception {
        //Given
        Account account = new Account(1, "Created at", "Updated at", "Password", "Email", "Country", "Language",
                Role.USER, false, new User(10, "Name", "Lastname", "Birth", Gender.FEMALE),
                new WatchList(100, new ArrayList<>(), new ArrayList<>()), new Buy(200, new ArrayList<>(), new ArrayList<>()),
                new ArrayList<>(), new ArrayList<>());
        AccountDto accountDto = new AccountDto(2, "Created at Dto", "Updated at Dto", "Password Dto",
                "Email Dto", "Country Dto", "Language Dto",
                Role.USER, false, 10, 100, 200, new ArrayList<>(), new ArrayList<>());

        when(accountService.save(account)).thenReturn(account);
        when(accountMapper.mapToAccountDto(account)).thenReturn(accountDto);
        when(accountMapper.mapToAccount(any(AccountDto.class))).thenReturn(account);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(accountDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .put("/v1/accounts")
        .contentType(MediaType.APPLICATION_JSON)
        .characterEncoding("UTF-8")
        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.password").value("Password Dto"))
                .andExpect(jsonPath("$.buyId").value(200));
    }

    @Test
    public void shouldGetAccountByEmailAndPassword() throws Exception {
        //Given
        Account account = new Account(1, "Created at", "Updated at", "Password", "Email", "Country", "Language",
                Role.USER, false, new User(10, "Name", "Lastname", "Birth", Gender.FEMALE),
                new WatchList(100, new ArrayList<>(), new ArrayList<>()), new Buy(200, new ArrayList<>(), new ArrayList<>()),
                new ArrayList<>(), new ArrayList<>());
        AccountDto accountDto = new AccountDto(2, "Created at Dto", "Updated at Dto", "Password Dto",
                "Email Dto", "Country Dto", "Language Dto",
                Role.USER, false, 10, 100, 200, new ArrayList<>(), new ArrayList<>());

        when(accountService.getAccountByEmailAndPassword("Email", "Password")).thenReturn(Optional.of(account));
        when(accountMapper.mapToAccountDto(account)).thenReturn(accountDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/accounts/byEmailAndPassword")
        .param("email", "Email")
        .param("password", "Password")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.createdAt").value("Created at Dto"))
                .andExpect(jsonPath("$.rentMoviesId.size()").value(0));
    }

    @Test
    public void shouldGetWatchlistForAccount() throws Exception {
        //Given
        WatchList watchList = new WatchList(1, movies, tvShows);
        WatchListDto watchListDto = new WatchListDto(2, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList()));

        when(accountService.getWatchListForAccount(any())).thenReturn(watchList);
        when(watchListMapper.mapToWatchListDto(watchList)).thenReturn(watchListDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/accounts/{accountId}/watchlist", 5)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.moviesId.size()").value(2))
                .andExpect(jsonPath("$.tvShowsId.size()").value(2));
    }

    @Test
    public void shouldGetRentedMoviesForAccount() throws Exception {
        //Given
        List<MovieDto> movieDtos = List.of(new MovieDto("Title test", 100, "RD", 600, 300, 7.2, "Overview"));
        when(accountService.getAllRentedMoviesForAccount(any())).thenReturn(movies);
        when(movieMapper.mapToListMovieDto(movies)).thenReturn(movieDtos);

        //When & THen
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/accounts/{accountId}/rentedMovies", 3)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Title test"));
    }

    @Test
    public void shouldGetRentedTvShowsForAccount() throws Exception {
        //Given
        List<TvShowDto> tvShowDtos = List.of(new TvShowDto(300, "Daredevil", 10000, "1-1-2016", 8.5, 9540, "Overview"));
        when(accountService.getAllRentedTvShowsForAccount(any())).thenReturn(tvShows);
        when(tvShowMapper.mapToListTvShowDto(tvShows)).thenReturn(tvShowDtos);

        //When & THen
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/accounts/{accountId}/rentedTvShows", 15)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Daredevil"))
                .andExpect(jsonPath("$[0].first_air_date").value("1-1-2016"));
    }

    @Test
    public void shouldGetRentMovieForAccount() throws Exception {
        //Given
        List<RentMovie> rentMovieList = List.of(new RentMovie(1, new Account(), "Rent date", "Expire date", movies.get(1)));
        List<RentMovieDto> rentMovieDtoList = List.of(new RentMovieDto(2, 500, "Rent date DTO", "Expire date DTO", movies.get(1).getId()));

        when(accountService.getMovieRentsForAccount(any())).thenReturn(rentMovieList);
        when(rentMovieMapper.mapToListRentMovieDto(rentMovieList)).thenReturn(rentMovieDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/accounts/{accountId}/movieRents", 5)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].movieId").value(30));
    }

    @Test
    public void shouldGetRentTvShowsForAccount() throws Exception {
        //Given
        List<RentTvShow> rentTvShowList = List.of(new RentTvShow(1, new Account(), "Rent date", "Expire date", tvShows.get(1)));
        List<RentTvShowDto> rentTvShowDtoList = List.of(new RentTvShowDto(2, 999, "Rent date DTO", "Expire date DTO", tvShows.get(1).getId()));

        when(accountService.getTvShowRentsForAccount(any())).thenReturn(rentTvShowList);
        when(rentTvShowMapper.mapToListRentTvShowDto(rentTvShowList)).thenReturn(rentTvShowDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/accounts/{accountId}/tvShowRents", 222)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].tvShowId").value(50));
    }


    @Test
    public void shouldAuthenticateAccount() throws Exception {
        //Given
        when(accountService.authenticate("Email", "Password")).thenReturn(true);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
        .get("/v1/accounts/authenticate")
        .param("email", "Email")
        .param("password", "Password")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
