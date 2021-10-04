package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.springframework.stereotype.Service;

@Service
public class TvShowMapper {

    public TvShow mapToTvShow(TvShowDto tvShowDto) {
        return new TvShow(
                tvShowDto.getId(),
                tvShowDto.getName(),
                tvShowDto.getPopularity().doubleValue(),
                tvShowDto.getFirstAirDate(),
                tvShowDto.getVoteAverage().doubleValue(),
                tvShowDto.getVoteCount(),
                tvShowDto.getGenresId()
        );
    }

    public TvShowDto mapToTvShowDto(TvShow tvShow) {
        return new TvShowDto(
                tvShow.getId(),
                tvShow.getName(),
                tvShow.getPopularity(),
                tvShow.getFirstAirDate(),
                tvShow.getVoteAverage(),
                tvShow.getVoteCount(),
                tvShow.getGenresId()
        );
    }

}
