package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.domain.WatchList;
import com.myapp.vod_backend.exception.WatchListNotFoundException;
import com.myapp.vod_backend.repository.MovieRepository;
import com.myapp.vod_backend.repository.TvShowRepository;
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
@SpringBootTest
@RunWith(SpringRunner.class)
public class WatchListServiceTestSuite {

    @Autowired
    private WatchListService service;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TvShowRepository tvShowRepository;

    private WatchList watchList;
    private List<Movie> movies = new ArrayList<>();
    private List<TvShow> tvShows = new ArrayList<>();


    @BeforeEach
    public void beforeEachTest() {
        TvShow tvShow1 = new TvShow(1, "Daredevil", 500.0, "2016", 8.2, 20000, "Overview");
        TvShow tvShow2 = new TvShow(2, "Banshee", 300.0, "2013", 7.9, 15000, "Overview2");
        tvShowRepository.save(tvShow1);
        tvShowRepository.save(tvShow2);
        tvShows.add(tvShow1);
        tvShows.add(tvShow2);
        Movie movie1 = new Movie("Black Adam", 20, "Release2", 400.0, 300, 7.1, "Overview2");
        Movie movie2 = new Movie("Black Panther", 30, "Release3", 300.0, 400, 7.9, "Overview3");
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        movies.add(movie1);
        movies.add(movie2);
        watchList = new WatchList(null, movies, tvShows);
    }

    @Test
    public void shouldSaveWatchlist() {
        //Given

        //When
        WatchList result = service.save(watchList);

        //Then
        assertNotEquals(0, result.getId());
    }

    @Test
    public void shouldGetAllWatchlists() {
        //Given
        WatchList saved = service.save(watchList);

        //When
        List<WatchList> result = service.getWatchLists();

        //Then
        assertEquals(1, result.size());
    }

    @Test
    public void shouldGetWatchlist() throws WatchListNotFoundException {
        //Given
        WatchList saved = service.save(watchList);

        //When
        WatchList result = service.getWatchList(saved.getId()).orElseThrow(() -> new WatchListNotFoundException("Watchlist not found"));

        //Then
        assertEquals("Daredevil", result.getTvShows().get(0).getName());
    }

    @Test
    public void shouldDeleteWatchlist()  {
        //Given
        WatchList saved = service.save(watchList);

        //When
        service.deleteWatchList(saved.getId());

        //Then
        assertEquals(0, service.getWatchLists().size());
    }

    @Test
    public void shouldGetAllMoviesInWatchlist()  {
        //Given
        WatchList saved = service.save(watchList);

        //When
        List<Movie> result = service.getAllMoviesInWatchlist(saved.getId());

        //Then
        assertEquals(2, result.size());
    }

    @Test
    public void shouldGetAllTvShowsInWatchlist()  {
        //Given
        WatchList saved = service.save(watchList);

        //When
        List<TvShow> result = service.getAllTvShowsInWatchList(saved.getId());

        //Then
        assertEquals(2, result.size());
    }

    @Test
    public void shouldAddMovieToWatchlist() throws WatchListNotFoundException {
        //Given
        WatchList saved = service.save(watchList);
        Movie movie3 = new Movie("Test", 90, "Release5", 400.0, 100, 6.6, "Overview5");
        movieRepository.save(movie3);

        //When
        service.addMovieToWatchList(movie3.getId(), saved.getId());

        //Then
        assertEquals(3, service.getWatchLists().get(0).getMovies().size());
    }

    @Test
    public void shouldAddTvShowToWatchlist() throws WatchListNotFoundException {
        //Given
        WatchList saved = service.save(watchList);
        TvShow tvShow3 = new TvShow(6, "Test", 678.9, "2020", 4.5, 15000, "Overview6");
        tvShowRepository.save(tvShow3);

        //When
        service.addTvShowToWatchList(tvShow3.getId(), saved.getId());

        //Then
        assertEquals(3, service.getWatchLists().get(0).getTvShows().size());
    }

    @Test
    public void shouldDeleteMovieFromWatchlist() throws WatchListNotFoundException {
        //Given
        WatchList saved = service.save(watchList);

        //When
        service.deleteMovieFromWatchList(20, saved.getId());

        //Then
        assertEquals(1, service.getWatchLists().get(0).getMovies().size());
    }

    @Test
    public void shouldDeleteTvShowFromWatchlist() throws WatchListNotFoundException {
        //Given
        WatchList saved = service.save(watchList);

        //When
        service.deleteTvShowFromWatchList(1, saved.getId());

        //Then
        assertEquals(1, service.getWatchLists().get(0).getTvShows().size());
    }
}
