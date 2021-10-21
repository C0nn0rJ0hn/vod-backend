package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.MediaSearch;
import com.myapp.vod_backend.omdb.domain.MediaSearchDto;
import org.springframework.stereotype.Component;

@Component
public class OMDbMapper {

    public MediaSearchDto mapToMediaSearchDto(final MediaSearch mediaSearch) {
        return new MediaSearchDto(
                mediaSearch.getTitle(),
                mediaSearch.getYear(),
                mediaSearch.getReleased(),
                mediaSearch.getPlot(),
                mediaSearch.getPoster(),
                mediaSearch.getImdbRating(),
                mediaSearch.getImdbId()
        );
    }

    public MediaSearch mapToMediaSearch(final MediaSearchDto mediaSearchDto) {
        return new MediaSearch(
                mediaSearchDto.getTitle(),
                mediaSearchDto.getYear(),
                mediaSearchDto.getReleased(),
                mediaSearchDto.getPlot(),
                mediaSearchDto.getPoster(),
                mediaSearchDto.getImdbRating(),
                mediaSearchDto.getImdbId()
        );
    }
}
