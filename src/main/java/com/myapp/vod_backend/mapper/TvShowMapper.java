package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
                tvShowDto.getOverview()
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
                tvShow.getOverview()
        );
    }

    public List<TvShowDto> mapToListTvShowDto(List<TvShow> tvShows) {
        return tvShows.stream().map(this::mapToTvShowDto).collect(Collectors.toList());
    }

}
