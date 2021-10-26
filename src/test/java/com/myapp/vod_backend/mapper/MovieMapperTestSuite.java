package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieMapperTestSuite {

    @Autowired
    private MovieMapper mapper;

    @Test
    public void shouldMapToMovie() {
        //Given
        MovieDto movieDto = new MovieDto("Black Adam", 3, "2022", 400.0, 300, 7.1, "Overview Black Adam");

        //When
        Movie result = mapper.mapToMovie(movieDto);

        //Then
        assertEquals(3, result.getId());
        assertEquals("Black Adam", result.getTitle());
    }

    @Test
    public void shouldMapToMovieDto() {
        //Given
        Movie movie = new Movie("Avengers", 5, "2012", 400.0, 300, 7.1, "Overview Avengers");

        //When
        MovieDto result = mapper.mapToMovieDto(movie);

        //Then
        assertEquals(5, result.getId());
        assertEquals("Avengers", result.getTitle());
    }

    @Test
    public void shouldMapToListMovieDto() {
        //Given
        List<Movie> movies = List.of(new Movie("Avengers", 5, "2012", 400.0, 300, 7.1, "Overview Avengers"));

        //When
        List<MovieDto> result = mapper.mapToListMovieDto(movies);

        //Then
        assertEquals(1, result.size());
        assertEquals("Avengers", result.get(0).getTitle());
    }
}
