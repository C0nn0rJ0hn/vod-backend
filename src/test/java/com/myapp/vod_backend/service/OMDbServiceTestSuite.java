package com.myapp.vod_backend.service;

import com.myapp.vod_backend.omdb.client.OMDbClient;
import com.myapp.vod_backend.omdb.domain.MediaSearchDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OMDbServiceTestSuite {

    @InjectMocks
    private OMDbService service;

    @Mock
    private OMDbClient client;

    @Test
    public void shouldGetMediaDetails() {
        //Given
        MediaSearchDto mediaSearchDto = new MediaSearchDto("Test", "2012", "released", "plot",
                "poster", "7.7", "tt010101");

        when(client.getMediaDetails("Test")).thenReturn(mediaSearchDto);

        //When
        MediaSearchDto result = service.getMediaDetails("Test");

        //Then
        assertEquals("2012", result.getYear());
        assertEquals("tt010101", result.getImdbId());
    }
}
