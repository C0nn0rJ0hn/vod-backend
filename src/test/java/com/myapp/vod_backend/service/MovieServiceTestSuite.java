package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.exception.MovieNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class MovieServiceTestSuite {

    @Autowired
    private MovieService service;

    private Movie movie;
    private Movie movie2;
    private Movie movie3;


    @BeforeEach
    public void beforeEachTest() {
        movie = new Movie("Title1", 10, "Release1", 500.0, 200, 6.6, "Overview1");
        movie2 = new Movie("Black Adam", 20, "Release2", 400.0, 300, 7.1, "Overview2");
        movie3 = new Movie("Black Panther", 30, "Release3", 300.0, 400, 7.9, "Overview3");
        service.saveMovie(movie);
        service.saveMovie(movie2);
    }


    @Test
    public void shouldSaveMovie() {
        //Given

        //When
        Movie result = service.saveMovie(movie3);

        //Then
        assertEquals(3, service.getMovies().size());
    }

    @Test
    public void shouldGetMovie() {
        //Given
        Movie saved = service.saveMovie(movie3);

        //When
        Movie result = service.getMovieById(saved.getId()).orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        //Then
        assertEquals("Release3", result.getReleaseDate());
    }

    @Test
    public void shouldGetAllMovies() {
        //Given
        Movie saved = service.saveMovie(movie3);

        //When
        List<Movie> result = service.getMovies();

        //Then
        assertEquals(3, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    public void shouldDeleteMovie() {
        //Given
        Movie saved = service.saveMovie(movie3);

        //When
        service.deleteMovieById(saved.getId());

        //Then
        assertEquals(2, service.getMovies().size());
    }

    @Test
    public void shouldGetMoviesWithAverageAbove() {
        //Given
        service.saveMovie(movie3);

        //When
        List<Movie> result = service.getMoviesWithAverageAbove(7.0);

        //Then
        assertEquals(2, result.size());
    }

    @Test
    public void shouldGetMoviesByKeyword() {
        //Given
        service.saveMovie(movie3);

        //When
        List<Movie> result = service.getMoviesByKeyword("black");

        //Then
        assertEquals(2, result.size());
    }
}
