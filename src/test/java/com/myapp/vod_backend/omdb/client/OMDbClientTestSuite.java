package com.myapp.vod_backend.omdb.client;

import com.myapp.vod_backend.omdb.config.OMDbConfig;
import com.myapp.vod_backend.omdb.domain.MediaSearchDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OMDbClientTestSuite {

    @InjectMocks
    private OMDbClient omDbClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OMDbConfig omDbConfig;

    @Test
    public void fetchMediaDetails() throws URISyntaxException {
        //Given
        String title = "Test";
        when(omDbConfig.getOmdbApiDataEndpoint()).thenReturn("http://test.com");
        when(omDbConfig.getOmdbApiKey()).thenReturn("test");

        MediaSearchDto search = new MediaSearchDto(title, "2010", "Released", "Plot", "Poster path", "7,7", "tt20050");

        URI url = new URI("http://test.com?apikey=test&t=" + title);

        when(restTemplate.getForObject(url, MediaSearchDto.class)).thenReturn(search);

        //When
        MediaSearchDto result = omDbClient.getMediaDetails(title);

        //Then
        Assertions.assertEquals("Test", result.getTitle());
        Assertions.assertEquals("tt20050", result.getImdbId());
    }

}
