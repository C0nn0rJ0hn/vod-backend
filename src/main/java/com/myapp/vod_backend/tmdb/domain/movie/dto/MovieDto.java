package com.myapp.vod_backend.tmdb.domain.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDto {

    @JsonProperty("title")
    private String title;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("popularity")
    private Number popularity;

    @JsonProperty("vote_count")
    private Integer voteCount;

    @JsonProperty("vote_average")
    private Number voteAverage;

    @JsonProperty("genre_ids")
    private List<Integer> genresId;

   /* @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("video")
    private boolean video;*/
}
