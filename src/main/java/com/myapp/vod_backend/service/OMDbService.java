package com.myapp.vod_backend.service;

import com.myapp.vod_backend.omdb.client.OMDbClient;
import com.myapp.vod_backend.omdb.domain.MediaSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OMDbService {

    private final OMDbClient omDbClient;

    public byte[] getPosterForMedia(String imdbId) {
        return omDbClient.getPosterForMedia(imdbId);
    }

    public MediaSearchDto getMediaDetails(String title) {
        return omDbClient.getMediaDetails(title);
    }
}
