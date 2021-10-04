package com.myapp.vod_backend.tmdb.domain.tvshow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularTvShowsDto {
    private Integer page;
    private List<TvShowDto> results;
    private Integer totalResults;
    private Integer totalPages;
}
