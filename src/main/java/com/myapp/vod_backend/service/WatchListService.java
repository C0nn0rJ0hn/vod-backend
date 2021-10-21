package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.domain.WatchList;
import com.myapp.vod_backend.exception.MovieNotFoundException;
import com.myapp.vod_backend.exception.TvShowNotFoundException;
import com.myapp.vod_backend.exception.WatchListNotFoundException;
import com.myapp.vod_backend.repository.MovieRepository;
import com.myapp.vod_backend.repository.TvShowRepository;
import com.myapp.vod_backend.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TvShowRepository tvShowRepository;


    public WatchList save(final WatchList watchList) {
        return watchListRepository.save(watchList);
    }

    public List<WatchList> getWatchLists() {
        return watchListRepository.findAll();
    }

    public Optional<WatchList> getWatchList(final Integer watchListId) {
        return watchListRepository.findById(watchListId);
    }

    public void deleteWatchList(final Integer watchListId) {
        watchListRepository.deleteById(watchListId);
    }

    public List<Movie> getAllMoviesInWatchlist(final Integer watchListId) {
        return watchListRepository.findById(watchListId).map(WatchList::getMovies).orElse(new ArrayList<>());
    }

    public List<TvShow> getAllTvShowsInWatchList(final Integer watchListId) {
        return watchListRepository.findById(watchListId).map(WatchList::getTvShows).orElse(new ArrayList<>());
    }

    public void addMovieToWatchList(final Integer movieId, final Integer watchListId) throws WatchListNotFoundException {
        WatchList watchList = watchListRepository.findById(watchListId).orElseThrow(() -> new WatchListNotFoundException("Watchlist not found in database"));
        Movie movie  = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found in database"));
        watchList.getMovies().add(movie);
        watchListRepository.save(watchList);
    }

    public void addTvShowToWatchList(final Integer tvShowId, final Integer watchListId) throws WatchListNotFoundException{
        WatchList watchList = watchListRepository.findById(watchListId).orElseThrow(() -> new WatchListNotFoundException("Watchlist not found in database"));
        TvShow tvShow = tvShowRepository.findById(tvShowId).orElseThrow(() -> new TvShowNotFoundException("Tv show not found in database"));
        watchList.getTvShows().add(tvShow);
        watchListRepository.save(watchList);
    }

    public void deleteMovieFromWatchList(final Integer movieId, final Integer watchListId) throws WatchListNotFoundException{
        WatchList watchList = watchListRepository.findById(watchListId).orElseThrow(() -> new WatchListNotFoundException("Watchlist not found in database"));
        for (int i = 0; i < watchList.getMovies().size(); i++) {
            if (watchList.getMovies().get(i).getId().equals(movieId)) {
                watchList.getMovies().remove(watchList.getMovies().get(i));
            }
            else {
                throw new MovieNotFoundException("Movie not found in current watchlist");
            }
        }
        watchListRepository.save(watchList);
    }

    public void deleteTvShowFromWatchList(final Integer tvShowId, final Integer watchListId) throws WatchListNotFoundException{
        WatchList watchList = watchListRepository.findById(watchListId).orElseThrow(() -> new WatchListNotFoundException("Watchlist not found in database"));
        for (int i = 0; i < watchList.getTvShows().size(); i++) {
            if (watchList.getTvShows().get(i).getId().equals(tvShowId)) {
                watchList.getTvShows().remove(watchList.getTvShows().get(i));
            }
            else {
                throw new TvShowNotFoundException("Tv show not found in current watchlist");
            }
        }
        watchListRepository.save(watchList);
    }
}
