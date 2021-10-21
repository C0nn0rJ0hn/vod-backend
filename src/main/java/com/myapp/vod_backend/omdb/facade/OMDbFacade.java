package com.myapp.vod_backend.omdb.facade;

import com.myapp.vod_backend.mapper.OMDbMapper;
import com.myapp.vod_backend.omdb.domain.MediaSearchDto;
import com.myapp.vod_backend.service.OMDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OMDbFacade {

    @Autowired
    private OMDbMapper mapper;

    @Autowired
    private OMDbService service;

    public byte[] fetchPosterForMedia(String imdbId) {
        return service.getPosterForMedia(imdbId);
    }

    public MediaSearchDto fetchMediaDetails(String title) {
        return mapper.mapToMediaSearchDto(mapper.mapToMediaSearch(service.getMediaDetails(title)));
    }
}
