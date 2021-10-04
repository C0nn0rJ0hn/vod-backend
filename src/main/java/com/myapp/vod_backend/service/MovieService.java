package com.myapp.vod_backend.service;

import com.myapp.vod_backend.domain.Movie;
import com.myapp.vod_backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
