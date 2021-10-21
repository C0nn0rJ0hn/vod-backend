package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.Buy;
import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.exception.BuyNotFoundException;
import com.myapp.vod_backend.exception.MovieNotFoundException;
import com.myapp.vod_backend.exception.TvShowNotFoundException;
import com.myapp.vod_backend.repository.BuyRepository;
import com.myapp.vod_backend.repository.MovieRepository;
import com.myapp.vod_backend.repository.TvShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyService {

    @Autowired
    private BuyRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TvShowRepository tvShowRepository;

    public Buy save(final Buy buy) {
        return repository.save(buy);
    }

    public List<Buy> getBuys() {
        return repository.findAll();
    }

    public Optional<Buy> getBuy(final Integer buyId) {
        return repository.findById(buyId);
    }

    public void deleteBuy(final Integer buyId) {
        repository.deleteById(buyId);
    }

    public Buy buyMovie(final Integer movieId, final Integer buyId) throws BuyNotFoundException {
        Buy buy = repository.findById(buyId).orElseThrow(() -> new BuyNotFoundException("Buy not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found in database"));
        buy.getMovies().add(movie);
        return repository.save(buy);
    }

    public Buy buyTvShow(final Integer tvShowId, final Integer buyId) throws BuyNotFoundException {
        Buy buy = repository.findById(buyId).orElseThrow(() -> new BuyNotFoundException("Buy not found"));
        TvShow tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new TvShowNotFoundException("Tv show not found in databse"));
        buy.getTvShows().add(tvShow);
        return repository.save(buy);
    }

    public List<TvShow> getBoughtTvShows(Integer buyId) throws BuyNotFoundException {
        Buy buy = repository.findById(buyId).orElseThrow(() -> new BuyNotFoundException("Buy not found"));
        return buy.getTvShows();
    }

    public List<Movie> getBoughtMovies(Integer buyId) throws BuyNotFoundException {
        Buy buy = repository.findById(buyId).orElseThrow(() -> new BuyNotFoundException("Buy not found"));
        return buy.getMovies();
    }
}
