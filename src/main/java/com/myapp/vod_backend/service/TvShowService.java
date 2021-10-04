package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.repository.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TvShowService {

    @Autowired
    private TvShowRepository repository;

    public TvShow save(final TvShow tvShow) {
        return repository.save(tvShow);
    }

    public List<TvShow> getTvShows() {
        return repository.findAll();
    }

    public Optional<TvShow> getTvShowById(final Integer tvShowId) {
        return repository.findById(tvShowId);
    }

    public void deleteTvShowById(final Integer tvShowId) {
        repository.deleteById(tvShowId);
    }
}
