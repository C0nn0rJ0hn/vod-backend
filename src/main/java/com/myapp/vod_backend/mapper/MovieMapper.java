package com.myapp.vod_backend.mapper;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.tmdb.domain.movie.dto.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieMapper {

    public Movie mapToMovie(final MovieDto movieDto) {
        return new Movie(
                movieDto.getTitle(),
                movieDto.getId(),
                movieDto.getReleaseDate(),
                movieDto.getPopularity().doubleValue(),
                movieDto.getVoteCount(),
                movieDto.getVoteAverage().doubleValue(),
                movieDto.getOverview()
        );
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        return new MovieDto(
                movie.getTitle(),
                movie.getId(),
                movie.getReleaseDate(),
                movie.getPopularity(),
                movie.getVoteCount(),
                movie.getVoteAverage(),
                movie.getOverview()
        );
    }

    public List<MovieDto> mapToListMovieDto(List<Movie> movies) {
        return movies.stream().map(this::mapToMovieDto).collect(Collectors.toList());
    }
}
