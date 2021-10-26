package com.myapp.vod_backend.service;


import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.exception.TvShowNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TvShowServiceTestSuite {

    @Autowired
    private TvShowService service;

    private TvShow tvShow;
    private TvShow tvShow2;
    private TvShow tvShow3;


    @BeforeEach
    public void beforeEachTest() {
        tvShow = new TvShow(1, "Daredevil", 500.0, "2016", 8.2, 20000, "Overview");
        tvShow2 = new TvShow(2, "Banshee", 300.0, "2013", 7.9, 15000, "Overview2");
        tvShow3 = new TvShow(3, "Game Of Thrones", 50000.0, "2014", 8.5, 1500000, "Overview3");
        service.save(tvShow);
        service.save(tvShow2);
    }


    @Test
    public void shouldSaveTvShow() {
        //Given

        //When
        TvShow result = service.save(tvShow3);

        //Then
        assertEquals(3, service.getTvShows().size());
    }

    @Test
    public void shouldGetTvShow() {
        //Given
        TvShow saved = service.save(tvShow3);

        //When
        TvShow result = service.getTvShowById(saved.getId()).orElseThrow(() -> new TvShowNotFoundException("Tv show not found"));

        //Then
        assertEquals("Game Of Thrones", result.getName());
    }

    @Test
    public void shouldGetAllTvShows() {
        //Given
        service.save(tvShow3);

        //When
        List<TvShow> result = service.getTvShows();

        //Then
        assertEquals(3, result.size());
        assertEquals("Daredevil", result.get(0).getName());
    }

    @Test
    public void shouldDeleteTvShow() {
        //Given
        TvShow saved = service.save(tvShow3);

        //When
        service.deleteTvShowById(saved.getId());

        //Then
        assertEquals(2, service.getTvShows().size());
    }

    @Test
    public void shouldGetTvShowsWithAverageAbove() {
        //Given
        service.save(tvShow3);

        //When
        List<TvShow> result = service.getTvShowsWithAverageAbove(7.5);

        //Then
        assertEquals(3, result.size());
    }

    @Test
    public void shouldGetMoviesByKeyword() {
        //Given
        service.save(tvShow3);

        //When
        List<TvShow> result = service.getTvShowsByKeyword("game");

        //Then
        assertEquals(1, result.size());
    }
}
