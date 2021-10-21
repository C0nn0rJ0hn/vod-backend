package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.Account;
import com.myapp.vod_backend.domain.RentMovie;
import com.myapp.vod_backend.domain.RentTvShow;
import com.myapp.vod_backend.domain.dto.AccountDto;
import com.myapp.vod_backend.exception.MovieNotFoundException;
import com.myapp.vod_backend.exception.TvShowNotFoundException;
import com.myapp.vod_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WatchListRepository watchListRepository;

    @Autowired
    private RentMovieRepository rentMovieRepository;

    @Autowired
    private RentTvShowRepository rentTvShowRepository;

    @Autowired
    private BuyRepository buyRepository;

    public Account mapToAccount(AccountDto accountDto) {
        return new Account(
                accountDto.getId(),
                accountDto.getCreatedAt(),
                accountDto.getUpdatedAt(),
                accountDto.getPassword(),
                accountDto.getEmail(),
                accountDto.getCountry(),
                accountDto.getLanguage(),
                accountDto.getRole(),
                accountDto.isLoggedIn(),
                accountDto.getUserId() != null ? userRepository.findById(accountDto.getUserId()).orElse(null) : null,
                accountDto.getWatchlistId() != null ? watchListRepository.findById(accountDto.getWatchlistId()).orElse(null) : null,
                accountDto.getBuyId() != null ? buyRepository.findById(accountDto.getBuyId()).orElse(null) : null,
                accountDto.getRentMoviesId().stream().map(rentMovieRepository::findById)
                        .map(m -> m.orElseThrow(() -> new MovieNotFoundException("Movie not found"))).collect(Collectors.toList()),
                accountDto.getRentTvShowsId().stream().map(rentTvShowRepository::findById)
                        .map(t -> t.orElseThrow(() -> new TvShowNotFoundException("Tv show not  found"))).collect(Collectors.toList())
        );
    }

    public AccountDto mapToAccountDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getCreatedAt(),
                account.getUpdatedAt(),
                account.getPassword(),
                account.getEmail(),
                account.getCountry(),
                account.getLanguage(),
                account.getRole(),
                account.isLoggedIn(),
                account.getUser() != null ? account.getUser().getId() : null,
                account.getWatchList() != null ? account.getWatchList().getId() : null,
                account.getBuy() != null ? account.getBuy().getId() : null,
                account.getRentMovies().stream().map(RentMovie::getId).collect(Collectors.toList()),
                account.getRentTvShows().stream().map(RentTvShow::getId).collect(Collectors.toList())
        );
    }

    public List<AccountDto> mapToListAccountDto(List<Account> accounts) {
        return accounts.stream().map(this::mapToAccountDto).collect(Collectors.toList());
    }
}
