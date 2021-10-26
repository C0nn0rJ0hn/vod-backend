package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.domain.WatchList;
import com.myapp.vod_backend.domain.dto.WatchListDto;
import com.myapp.vod_backend.repository.MovieRepository;
import com.myapp.vod_backend.repository.TvShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class WatchListMapperTestSuite {

    @Autowired
    private WatchListMapper mapper;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private TvShowRepository tvShowRepository;

    private List<Movie> movies;
    private List<TvShow> tvShows;

    @BeforeEach
    public void beforeEachTest() {
        movies = List.of(new Movie("Black Adam", 3, "2022", 400.0, 300, 7.1, "Overview Black Adam"),
                new Movie("Black Panther", 4, "2019", 300.0, 400, 7.9, "Overview Black Panther"));
        tvShows = List.of(new TvShow(1, "Daredevil", 500.0, "2016", 8.2, 20000, "Overview Daredevil"),
                new TvShow(2, "Banshee", 300.0, "2013", 7.9, 15000, "Overview Banshee"));
    }

    @Test
    public void shouldMapToWatchlist() {
        //Given
        WatchListDto watchListDto = new WatchListDto(100, movies.stream().map(Movie::getId).collect(Collectors.toList()),
                tvShows.stream().map(TvShow::getId).collect(Collectors.toList()));

        when(movieRepository.findById(3)).thenReturn(Optional.of(movies.get(0)));
        when(movieRepository.findById(4)).thenReturn(Optional.of(movies.get(1)));

        when(tvShowRepository.findById(1)).thenReturn(Optional.of(tvShows.get(0)));
        when(tvShowRepository.findById(2)).thenReturn(Optional.of(tvShows.get(1)));


        //When
        WatchList result = mapper.mapToWatchList(watchListDto);

        //Then
        assertEquals(2, result.getTvShows().size());
        assertEquals(2, result.getMovies().size());
        assertEquals(100, result.getId());
    }

    @Test
    public void shouldMapToWatchlistDto() {
        //Given
        WatchList watchList = new WatchList(50, movies, tvShows);

        //When
        WatchListDto result = mapper.mapToWatchListDto(watchList);

        //Then
        assertEquals(2, result.getTvShowsId().size());
        assertEquals(2, result.getMoviesId().size());
        assertEquals(50, result.getId());
    }

    @Test
    public void shouldMapToListWatchlistDto() {
        //Given
        List<WatchList> watchLists = List.of(new WatchList(50, movies, tvShows));

        //When
        List<WatchListDto> result = mapper.mapToListWatchListDto(watchLists);

        //Then
        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getTvShowsId().size());
        assertEquals(3, result.get(0).getMoviesId().get(0));
    }
}
