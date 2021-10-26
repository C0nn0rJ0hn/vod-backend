package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
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
public class TMDBMapperTestSuite {

    @Autowired
    private TMDBMapper mapper;

    @Test
    public void shouldMapToMovies() {
        //Given
        List<MovieDto> movieDtoList = List.of(new MovieDto("Black Adam", 20, "2022", 400.0,
                300, 7.1, "Overview Black Adam"));

        //When
        List<Movie> result = mapper.mapToMovies(movieDtoList);

        //Then
        assertEquals(1, result.size());
        assertEquals("Black Adam", result.get(0).getTitle());
    }

    @Test
    public void shouldMapToMoviesDto() {
        //Given
        List<Movie> movies = List.of(new Movie("Shazam", 31, "2019", 400.0,
                300, 7.1, "Overview Shazam"));

        //When
        List<MovieDto> result = mapper.mapToMoviesDto(movies);

        //Then
        assertEquals(1, result.size());
        assertEquals("Shazam", result.get(0).getTitle());
        assertEquals(31, result.get(0).getId());
    }

    @Test
    public void shouldMapToTvShow() {
        //Given
        List<TvShowDto> tvShowDtoList = List.of(new TvShowDto(1, "Daredevil", 500.0, "2016",
                8.2, 20000, "Overview"));

        //When
        List<TvShow> result = mapper.mapToTvShows(tvShowDtoList);

        //Then
        assertEquals(1, result.size());
        assertEquals("Daredevil", result.get(0).getName());
    }

    @Test
    public void shouldMapToTvShowDto() {
        //Given
        List<TvShow> tvShows = List.of(new TvShow(18, "True Detective", 500.0, "2016",
                8.2, 20000, "Overview"));

        //When
        List<TvShowDto> result = mapper.mapToTvShowsDto(tvShows);

        //Then
        assertEquals(1, result.size());
        assertEquals("True Detective", result.get(0).getName());
    }
}
