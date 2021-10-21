package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public Movie saveMovie (final Movie movie) {
        return repository.save(movie);
    }

    public List<Movie> getMovies() {
        return repository.findAll();
    }

    public Optional<Movie> getMovieById(final Integer movieId) {
        return repository.findById(movieId);
    }

    public void deleteMovieById(final Integer movieId) {
        repository.deleteById(movieId);
    }

    public List<Movie> getMoviesWithAverageAbove(Double voteAverage) {
        List<Movie> result = new ArrayList<>();
        List<Movie> movies = repository.findAll();

        for (Movie movie : movies) {
            if (movie.getVoteAverage() >= voteAverage) {
                result.add(movie);
            }
        }
        return result;
    }

    public List<Movie> getMoviesByKeyword(String keyword) {
        List<Movie> movies = new ArrayList<>();
        List<Movie> allMovies = repository.findAll();

        for (Movie movie : allMovies) {
            if (movie.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                movies.add(movie);
            }
        }
        return movies;
    }
}
