package com.myapp.vod_backend.tmdb.domain.tvshow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularTvShowsDto {

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("results")
    private List<TvShowDto> results;

    @JsonProperty("total_results")
    private Integer totalResults;

    @JsonProperty("total_pages")
    private Integer totalPages;
}
