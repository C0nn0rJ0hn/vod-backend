package com.myapp.vod_backend.repository;

import com.myapp.vod_backend.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MovieRepository extends CrudRepository<Movie, Integer> {

    @Override
    Movie save(Movie movie);

    @Override
    List<Movie> findAll();

    @Override
    Optional<Movie> findById(Integer movieId);

    void deleteById(Integer movieId);
}
