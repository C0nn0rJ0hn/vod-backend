package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.domain.TvShow;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import com.myapp.vod_backend.tmdb.domain.tvshow.dto.TvShowDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TMDBMapper {

    public List<Movie> mapToMovies(final List<MovieDto> movieDtoList) {
        return movieDtoList.stream()
                .map(movie -> new Movie(movie.getTitle(), movie.getId(), movie.getReleaseDate(), movie.getPopularity().doubleValue(),
                        movie.getVoteCount(), movie.getVoteAverage().doubleValue(), movie.getOverview()))
                .collect(Collectors.toList());
    }

    public List<MovieDto> mapToMoviesDto(final List<Movie> movies) {
        return movies.stream()
                .map(movie -> new MovieDto(movie.getTitle(), movie.getId(), movie.getReleaseDate(),
                        movie.getPopularity(), movie.getVoteCount(), movie.getVoteAverage(), movie.getOverview()))
                .collect(Collectors.toList());
    }

    public List<TvShow> mapToTvShows(final List<TvShowDto> tvShowDtoList) {
        return tvShowDtoList.stream()
                .map(tv -> new TvShow(tv.getId(), tv.getName(), tv.getPopularity().doubleValue(), tv.getFirstAirDate(),
                        tv.getVoteAverage().doubleValue(), tv.getVoteCount(), tv.getOverview()))
                .collect(Collectors.toList());
    }

    public List<TvShowDto> mapToTvShowsDto(final List<TvShow> tvShows) {
        return tvShows.stream()
                .map(tv -> new TvShowDto(tv.getId(), tv.getName(), tv.getPopularity(), tv.getFirstAirDate(),
                        tv.getVoteAverage(), tv.getVoteCount(), tv.getOverview()))
                .collect(Collectors.toList());
    }
}
