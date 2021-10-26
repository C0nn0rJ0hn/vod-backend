package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.Buy;
import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.exception.BuyNotFoundException;
import com.myapp.vod_backend.repository.MovieRepository;
import com.myapp.vod_backend.repository.TvShowRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
public class BuyServiceTestSuite {

    @Autowired
    private BuyService buyService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TvShowRepository tvShowRepository;

    private List<Movie> movies = new ArrayList<>();
    private List<TvShow> tvShows = new ArrayList<>();
    private Buy buy;

    @BeforeEach
    public void beforeEachTest() {
        Movie movie = new Movie("Title", 100, "2020", 130.00, 300, 7.7, "Overview");
        Movie savedMovie = movieRepository.save(movie);

        TvShow tvShow = new TvShow(200, "Name", 150.50, "2021", 8.0, 300, "Overview");
        TvShow savedTvShow = tvShowRepository.save(tvShow);

        movies.add(savedMovie);
        tvShows.add(savedTvShow);
        buy = new Buy(null, movies, tvShows);
    }

    @Test
    public void shouldSaveBuy() {
        //Given


        //When
        Buy saved = buyService.save(buy);

        //Then
        assertNotEquals(0, saved.getId());
        assertEquals(300, saved.getTvShows().get(0).getVoteCount());
    }

    @Test
    public void shouldGetAllBuys() {
        //Given
        buyService.save(buy);

        //When
        List<Buy> buys = buyService.getBuys();

        //Then
        assertEquals(1, buys.size());
        assertEquals(100, buys.get(0).getMovies().get(0).getId());
    }

    @Test
    public void shouldGetBuy() throws BuyNotFoundException {
        //Given
        Buy saved = buyService.save(buy);

        //When
        Buy result = buyService.getBuy(saved.getId()).orElseThrow(() -> new BuyNotFoundException("Buy not found"));

        //Then
        assertEquals("Name", result.getTvShows().get(0).getName());
    }

    @Test
    public void shouldDeleteBuy() {
        //Given
        Buy saved = buyService.save(buy);

        //When
        buyService.deleteBuy(saved.getId());

        //Then
        assertEquals(0, buyService.getBuys().size());
    }

    @Test
    public void shouldBuyMovie() throws BuyNotFoundException {
        //Given
        Buy saved = buyService.save(buy);
        Movie movie = new Movie("Test", 10, "2017", 187.40, 1000, 8.7, "Overview test");
        Movie savedMovie = movieRepository.save(movie);

        //When
        Buy result= buyService.buyMovie(savedMovie.getId(), saved.getId());

        //Then
        assertEquals(2, result.getMovies().size());
        assertEquals(10, result.getMovies().get(1).getId());
    }

    @Test
    public void shouldBuyTvShow() throws BuyNotFoundException {
        //Given
        Buy saved = buyService.save(buy);
        TvShow tv = new TvShow(500, "Test name", 1000.5, "2010", 7.2, 150, "Overview test");
        TvShow savedTvShow = tvShowRepository.save(tv);

        //When
        Buy result = buyService.buyTvShow(savedTvShow.getId(), saved.getId());

        //Then
        assertEquals(2, buyService.getBuys().get(0).getTvShows().size());
        assertEquals("Test name", buyService.getBuys().get(0).getTvShows().get(1).getName());
    }

    @Test
    public void getBoughtMovies() throws BuyNotFoundException {
        //Given
        Buy saved = buyService.save(buy);

        //When
        List<Movie> boughtMovies = buyService.getBoughtMovies(saved.getId());

        //Then
        assertEquals(1, boughtMovies.size());
    }

    @Test
    public void getBoughtTvShows() throws BuyNotFoundException {
        //Given
        Buy saved = buyService.save(buy);

        //When
        List<TvShow> boughtTvShows = buyService.getBoughtTvShows(saved.getId());

        //Then
        assertEquals(1, boughtTvShows.size());
    }
}
