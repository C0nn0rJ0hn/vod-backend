package com.myapp.vod_backend.controller;

import com.myapp.vod_backend.domain.dto.AccountDto;
import com.myapp.vod_backend.domain.dto.RentMovieDto;
import com.myapp.vod_backend.domain.dto.RentTvShowDto;
import com.myapp.vod_backend.domain.dto.WatchListDto;
import com.myapp.vod_backend.exception.AccountNotFoundException;
import com.myapp.vod_backend.mapper.*;
import com.myapp.vod_backend.service.AccountService;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1")
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private AccountMapper mapper;

    @Autowired
    private WatchListMapper watchListMapper;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private TvShowMapper tvShowMapper;

    @Autowired
    private RentMovieMapper rentMovieMapper;

    @Autowired
    private RentTvShowMapper rentTvShowMapper;

    @GetMapping(value = "/accounts")
    public List<AccountDto> getAccounts() {
        return mapper.mapToListAccountDto(service.getAccounts());
    }

    @GetMapping(value = "/accounts/{accountId}")
    public AccountDto getAccount(@PathVariable Integer accountId) throws AccountNotFoundException {
        return mapper.mapToAccountDto(service.getAccount(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found")));
    }

    @DeleteMapping(value = "/accounts/{accountId}")
    public void deleteAccount(@PathVariable Integer accountId) {
        service.deleteAccount(accountId);
    }

    @PostMapping(value = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createAccount(@RequestBody AccountDto accountDto) {
        service.create(mapper.mapToAccount(accountDto));
    }

    @PutMapping(value = "/accounts")
    public AccountDto updateAccount(@RequestBody AccountDto accountDto) {
        return mapper.mapToAccountDto(service.save(mapper.mapToAccount(accountDto)));
    }

    @GetMapping(value = "/accounts/byEmailAndPassword")
    public AccountDto getAccountByEmailAndPassword(@RequestParam String email, @RequestParam String password) throws AccountNotFoundException {
        return mapper.mapToAccountDto(service.getAccountByEmailAndPassword(email, password).orElseThrow(() -> new AccountNotFoundException("Account not found")));
    }

    @GetMapping(value = "/accounts/authenticate")
    public Boolean authenticate(@RequestParam String email, @RequestParam String password) {
        return service.authenticate(email, password);
    }

    @GetMapping(value = "/accounts/{accountId}/watchlist")
    public WatchListDto getWatchListForAccount(@PathVariable Integer accountId) throws AccountNotFoundException {
        return watchListMapper.mapToWatchListDto(service.getWatchListForAccount(accountId));
    }

    @GetMapping(value = "/accounts/{accountId}/rentedMovies")
    public List<MovieDto> getRentedMoviesForAccount(@PathVariable Integer accountId) throws AccountNotFoundException {
        return movieMapper.mapToListMovieDto(service.getAllRentedMoviesForAccount(accountId));
    }

    @GetMapping(value = "/accounts/{accountId}/rentedTvShows")
    public List<TvShowDto> getRentedTvShowsForAccount(@PathVariable Integer accountId) throws AccountNotFoundException {
        return tvShowMapper.mapToListTvShowDto(service.getAllRentedTvShowsForAccount(accountId));
    }

    @GetMapping(value = "/accounts/{accountId}/movieRents")
    public List<RentMovieDto> getMovieRentsForAccount(@PathVariable Integer accountId)throws AccountNotFoundException {
        return rentMovieMapper.mapToListRentMovieDto(service.getMovieRentsForAccount(accountId));
    }

    @GetMapping(value = "/accounts/{accountId}/tvShowRents")
    public List<RentTvShowDto> getTvShowRentsForAccount(@PathVariable Integer accountId)throws AccountNotFoundException {
        return rentTvShowMapper.mapToListRentTvShowDto(service.getTvShowRentsForAccount(accountId));
    }
}
