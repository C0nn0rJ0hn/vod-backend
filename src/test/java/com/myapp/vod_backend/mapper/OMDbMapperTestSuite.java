package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.MediaSearch;
import com.myapp.vod_backend.omdb.domain.MediaSearchDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OMDbMapperTestSuite {

    @Autowired
    private OMDbMapper mapper;

    @Test
    public void shouldMapToMediaSearch() {
        //Given
        MediaSearchDto mediaSearchDto = new MediaSearchDto("Title", "Year", "Released", "Plot",
                "Poster", "IMDB Rating", "IMDB ID");

        //When
        MediaSearch result = mapper.mapToMediaSearch(mediaSearchDto);

        //Then
        assertEquals("IMDB ID", result.getImdbId());
        assertEquals("Title", result.getTitle());
    }

    @Test
    public void shouldMapToMediaSearchDto() {
        //Given
        MediaSearch mediaSearch = new MediaSearch("Title", "Year", "Released", "Plot",
                "Poster", "IMDB Rating", "IMDB ID");

        //When
        MediaSearchDto result = mapper.mapToMediaSearchDto(mediaSearch);

        //Then
        assertEquals("Year", result.getYear());
        assertEquals("Poster", result.getPoster());
    }
}
