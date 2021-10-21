package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.exception.AccountNotFoundException;
import com.myapp.vod_backend.repository.AccountRepository;
import com.myapp.vod_backend.repository.BuyRepository;
import com.myapp.vod_backend.repository.RentMovieRepository;
import com.myapp.vod_backend.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private WatchListRepository watchListRepository;


    public Account create(final Account account) {
        Account savedAccount = accountRepository.save(account);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        savedAccount.setCreatedAt(now.format(formatter));
        savedAccount.setLoggedIn(false);

        WatchList watchList = new WatchList();
        WatchList savedWatchList = watchListRepository.save(watchList);
        savedAccount.setWatchList(savedWatchList);

        Buy buy = new Buy();
        Buy savedBuy = buyRepository.save(buy);
        savedAccount.setBuy(savedBuy);

        return accountRepository.save(savedAccount);
    }

    public Account save(final Account account) {
        Account savedAccount = accountRepository.save(account);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        savedAccount.setUpdatedAt(now.format(formatter));
        return accountRepository.save(savedAccount);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccount(final Integer accountId) {
        return accountRepository.findById(accountId);
    }

    public void deleteAccount(final Integer accountId) {
        accountRepository.deleteById(accountId);
    }

    public String generateFirstRandomPassword() {
        final String code = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(code.charAt(rnd.nextInt(code.length())));
        }
        return sb.toString();
    }

    public Optional<Account> getAccountByEmailAndPassword(final String email, final String password) {
        Account account = new Account();
        for (int i = 0; i < accountRepository.findAll().size(); i++) {
            if (accountRepository.findAll().get(i).getEmail().equals(email) && accountRepository.findAll().get(i).getPassword().equals(password)) {
                account = accountRepository.findAll().get(i);
            }
        }
        return Optional.of(account);
    }

    public boolean authenticate(final String email, final String password) {
        boolean isAuthenticated = true;
        if (accountRepository.findAll().size() == 0 || accountRepository.findAll().isEmpty()) {
            isAuthenticated = false;
        }
        for (int i = 0; i < accountRepository.findAll().size(); i++) {
            if (!accountRepository.findAll().get(i).getEmail().equals(email) || !accountRepository.findAll().get(i).getPassword().equals(password)) {
                isAuthenticated = false;
            }
            if (accountRepository.findAll().get(i).getEmail().equals(email) && accountRepository.findAll().get(i).getPassword().equals(password)) {
                Account account = accountRepository.findAll().get(i);
                account.setLoggedIn(true);
                isAuthenticated = true;
            }
        }
        return isAuthenticated;
    }

    public WatchList getWatchListForAccount(final Integer accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account.getWatchList();
    }

    public List<Movie> getAllRentedMoviesForAccount(final Integer accountId) throws AccountNotFoundException{
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account.getRentMovies().stream().map(RentMovie::getMovie).collect(Collectors.toList());
    }

    public List<TvShow> getAllRentedTvShowsForAccount(final Integer accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account.getRentTvShows().stream().map(RentTvShow::getTvShow).collect(Collectors.toList());
    }

    public List<RentMovie> getMovieRentsForAccount(final Integer accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account.getRentMovies();
    }

    public List<RentTvShow> getTvShowRentsForAccount(final Integer accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account.getRentTvShows();
    }
}
