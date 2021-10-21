package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.*;
import com.myapp.vod_backend.exception.TvShowNotFoundException;
import com.myapp.vod_backend.repository.AccountRepository;
import com.myapp.vod_backend.repository.RentTvShowRepository;
import com.myapp.vod_backend.repository.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RentTvShowService {

    @Autowired
    private RentTvShowRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TvShowRepository tvShowRepository;

    public List<RentTvShow> getAllRentsForTvShows() {
        return repository.findAll();
    }

    public Optional<RentTvShow> getRentForTvShow(final Integer rentTvShowId) {
        return repository.findById(rentTvShowId);
    }

    public void deleteRentTvShow(final Integer rentTvShowId) {
        repository.deleteById(rentTvShowId);
    }

    public RentTvShow saveRentTvShow(final RentTvShow rentTvShow) {
        return repository.save(rentTvShow);
    }

    public RentTvShow rentTvShow(final RentTvShow rentTvShow) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        rentTvShow.setRentDate(LocalDateTime.now().format(formatter));
        rentTvShow.setExpireDate(LocalDateTime.now().plusHours(48).format(formatter));

        return repository.save(rentTvShow);
    }




}
