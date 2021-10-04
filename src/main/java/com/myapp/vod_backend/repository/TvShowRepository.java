package com.myapp.vod_backend.repository;

import com.myapp.vod_backend.domain.TvShow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TvShowRepository extends CrudRepository<TvShow, Integer> {

    @Override
    TvShow save(TvShow tvShow);

    @Override
    List<TvShow> findAll();

    @Override
    Optional<TvShow> findById(Integer tvShowId);

    void deleteById(Integer tvShowId);
}
