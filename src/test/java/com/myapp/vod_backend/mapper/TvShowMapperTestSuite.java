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

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TvShowMapperTestSuite {

    @Autowired
    private TvShowMapper mapper;

    @Test
    public void shouldMapToTvShow() {
    //Given
    TvShowDto tvShowDto = new TvShowDto(99, "Game Of Thrones", 500.0, "2010", 8.2, 20000, "Overview GoT");

    //When
    TvShow result = mapper.mapToTvShow(tvShowDto);

    //Then
    assertEquals(99, result.getId());
    assertEquals("Game Of Thrones", result.getName());
}

    @Test
    public void shouldMapToTvShowDto() {
        //Given
        TvShow tvShow = new TvShow(15, "Warrior", 500.0, "2019", 8.2, 20000, "Overview Warrior");

        //When
        TvShowDto result = mapper.mapToTvShowDto(tvShow);

        //Then
        assertEquals(15, result.getId());
        assertEquals("Warrior", result.getName());
    }

    @Test
    public void shouldMapToListTvShowDto() {
        //Given
        List<TvShow> tvShows = List.of(new TvShow(15, "Warrior", 500.0, "2019", 8.2, 20000, "Overview Warrior"));

        //When
        List<TvShowDto> result = mapper.mapToListTvShowDto(tvShows);

        //Then
        assertEquals(1, result.size());
        assertEquals("Warrior", result.get(0).getName());
    }
}
